package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.dto.VacationRequestDTO;
import gr.knowledge.induction.enums.VacationStatus;
import gr.knowledge.induction.mapper.VacationRequestMapper;
import gr.knowledge.induction.repository.VacationRequestRepository;
import gr.knowledge.induction.service.EmployeeService;
import gr.knowledge.induction.service.VacationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.BiConsumer;

import static java.lang.Integer.valueOf;

/**
 * Υλοποίηση της υπηρεσίας VacationRequestService.
 * Παρέχει λειτουργίες δημιουργίας, ανάκτησης, ενημέρωσης, διαγραφής
 * και διαχείρισης αιτήσεων άδειας εργαζομένων.
 */
@Service
public class VacationRequestServiceImpl implements VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;

    private final VacationRequestMapper vacationRequestMapper;

    private final EmployeeService employeeService;

    private final Map<VacationStatus, BiConsumer<VacationRequestDTO, EmployeeDTO>> vacationActions;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param vacationRequestRepository το repository για αιτήσεις άδειας
     * @param employeeService           η υπηρεσία εργαζομένων
     * @param vacationRequestMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository, EmployeeService employeeService, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
        this.employeeService = employeeService;

        this.vacationActions = Map.of(
                VacationStatus.APPROVED, this::acceptRequest,
                VacationStatus.REJECTED, this::rejectRequest
        );
    }

    /**
     * Δημιουργεί μια νέα αίτηση άδειας.
     *
     * @param vacationRequest το αντικείμενο DTO που περιγράφει την αίτηση
     * @param holidays        λίστα με αργίες που δεν υπολογίζονται στα αιτούμενα ημέρες
     * @return το δημιουργημένο VacationRequestDTO
     * @throws IllegalArgumentException αν η ημερομηνία λήξης είναι πριν την ημερομηνία έναρξης
     */
    @Override
    public VacationRequestDTO createVacationRequest(VacationRequestDTO vacationRequest, List<LocalDate> holidays) {

        LocalDate start = vacationRequest.getStartDate();
        LocalDate end   = vacationRequest.getEndDate();
        Long employeeId = vacationRequest.getEmployee().getId();
        EmployeeDTO employee = vacationRequest.getEmployee();

        checkDates(employeeId, start, end);
        processingRequest(vacationRequest, holidays);

        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(vacationRequest)));
    }

    /**
     * Ελέγχει τις ημερομηνίες της αίτησης για επικαλύψεις ή λανθασμένη σειρά.
     *
     * @param employeeId το ID του εργαζομένου
     * @param startDate  ημερομηνία έναρξης
     * @param endDate    ημερομηνία λήξης
     * @throws IllegalArgumentException αν η λήξη είναι πριν την έναρξη ή αν υπάρχει επικαλυπτόμενη αίτηση
     */
    private void  checkDates(Long employeeId, LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Η ημερομηνία λήξης είναι πριν την ημερομηνία έναρξης!");
        }

        //αν εχουμε απο το query εστω και ενα count exception και ελεγχοσ status

        Integer result = vacationRequestRepository.countOfOverlappingRequests(employeeId, List.of(VacationStatus.PENDING , VacationStatus.APPROVED), startDate, endDate);
        if (result > 0) {
             throw new IllegalArgumentException();
        }
    }

    /**
     * Επεξεργάζεται την αίτηση αφαιρώντας σαββατοκύριακα και αργίες
     * και θέτει την κατάσταση σε PENDING.
     *
     * @param vacationRequest η αίτηση άδειας
     * @param holidays        λίστα αργιών
     */
    private void processingRequest(VacationRequestDTO vacationRequest, List<LocalDate> holidays ){
        int availableDays = vacationRequest.getEmployee().getVacationDays();
        int requestedDays = vacationRequest.getDays();
        LocalDate startDate = vacationRequest.getStartDate();
        LocalDate endDate = vacationRequest.getEndDate();

        Long totalDays = ChronoUnit.DAYS.between(startDate, endDate) ;
        for( int i = 0; i < totalDays + 1 ; i++){

            LocalDate currentDay = startDate.plusDays(i);

            if(currentDay.getDayOfWeek() == DayOfWeek.SATURDAY || currentDay.getDayOfWeek() == DayOfWeek.SUNDAY){
                requestedDays -= 1;
                availableDays += 1;

            } else if (holidays.contains(currentDay)) {
                requestedDays -= 1;
                availableDays += 1;

            }
            vacationRequest.setDays(requestedDays);
            vacationRequest.getEmployee().setVacationDays(availableDays);

        }

        vacationRequest.setStatus(VacationStatus.PENDING);

    }

    /**
     * Επιστρέφει όλες τις αιτήσεις άδειας.
     *
     * @return λίστα VacationRequestDTO
     */
    @Override
    public List<VacationRequestDTO> getAllVacationRequests() {
        return vacationRequestMapper.toDTO(vacationRequestRepository.findAll());
    }

    /**
     * Επιστρέφει μια αίτηση άδειας με βάση το ID.
     *
     * @param id το ID της αίτησης
     * @return VacationRequestDTO
     * @throws EntityNotFoundException αν δεν βρεθεί η αίτηση
     */
    @Override
    public VacationRequestDTO getVacationRequestById(Long id) {
        return vacationRequestMapper.toDTO(vacationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));
    }

    /**
     * Ενημερώνει μια αίτηση άδειας.
     *
     * @param id              το ID της αίτησης
     * @param vacationRequest τα ενημερωμένα δεδομένα
     * @return το ενημερωμένο VacationRequestDTO
     */
    @Override
    public VacationRequestDTO updateVacationRequest(Long id, VacationRequestDTO vacationRequest) {

        VacationRequestDTO currentVacationRequest = getVacationRequestById(id);
        vacationRequestMapper.updateEntityFromDTO(vacationRequestMapper.toEntity(currentVacationRequest), vacationRequest);

        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(currentVacationRequest)));
    }

    /**
     * Διαγράφει μια αίτηση άδειας.
     *
     * @param id το ID της αίτησης
     * @throws EntityNotFoundException αν δεν βρεθεί η αίτηση
     */
    @Override
    public void deleteVacationRequest(Long id) {
        VacationRequestDTO vacationRequest = vacationRequestMapper.toDTO(vacationRequestRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        vacationRequestRepository.deleteById(id);
    }

    /**
     * Δημιουργεί και αποθηκεύει νέα αίτηση άδειας θέτοντας την κατάσταση σε PENDING,
     * ελέγχοντας τα όρια ημερών.
     *
     * @param vacationRequest η αίτηση άδειας
     * @return το αποθηκευμένο VacationRequestDTO
     */
    @Override
    public VacationRequestDTO requestVacation(VacationRequestDTO vacationRequest) {
        Long employeeId = vacationRequest.getEmployee().getId();

        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);

        int remainingDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();
        int daysLimit = 5;


        if (requestedDays <= remainingDays) {
            if (requestedDays <= daysLimit) {
                vacationRequest.setStatus(VacationStatus.PENDING);
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(vacationRequest)));
    }

    /**
     * Χειρίζεται μια αίτηση άδειας εγκρίνοντας ή απορρίπτοντας
     * με βάση τις διαθέσιμες ημέρες του εργαζομένου.
     *
     * @param vacationRequest η αίτηση άδειας προς επεξεργασία
     * @return το ενημερωμένο VacationRequestDTO
     */
    @Override
    public VacationRequestDTO handleRequest(VacationRequestDTO vacationRequest){

        VacationRequestDTO currentVacationRequest = getVacationRequestById(vacationRequest.getId());
        Long employeeId = vacationRequest.getEmployee().getId();
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);

        int availableDays = Optional.ofNullable(employee.getVacationDays())
                .orElseThrow(() -> new IllegalArgumentException());

        int requestedDays = Optional.ofNullable(vacationRequest.getDays())
                .orElseThrow(() -> new IllegalArgumentException());

        if (requestedDays <= 0) {
            throw new IllegalArgumentException();
        }

       VacationStatus status = (requestedDays <= availableDays) ? VacationStatus.APPROVED : VacationStatus.REJECTED;

        vacationActions.get(status).accept(vacationRequest, employee);
        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(vacationRequest)));
    }

    /**
     * Εγκρίνει μια αίτηση άδειας και ενημερώνει τον εργαζόμενο.
     *
     * @param vacationRequest η αίτηση άδειας
     * @param employee        ο εργαζόμενος
     */
    private void acceptRequest(VacationRequestDTO vacationRequest, EmployeeDTO employee){

        vacationRequest.setStatus(VacationStatus.APPROVED);
        employeeService.saveEmployee(employee);

    }

    /**
     * Απορρίπτει μια αίτηση άδειας και επιστρέφει τις ημέρες στον εργαζόμενο.
     *
     * @param vacationRequest η αίτηση άδειας
     * @param employee        ο εργαζόμενος
     */
    private void rejectRequest(VacationRequestDTO vacationRequest, EmployeeDTO employee){

        int availableDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();

        employee.setVacationDays(availableDays += requestedDays);
        vacationRequest.setStatus(VacationStatus.REJECTED);
    }
}