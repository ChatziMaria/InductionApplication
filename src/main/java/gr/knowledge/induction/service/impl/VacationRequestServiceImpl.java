package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.domain.VacationStatus;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.dto.VacationRequestDTO;
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

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;

    private final VacationRequestMapper vacationRequestMapper;

    private final EmployeeService employeeService;

    private final Map<VacationStatus, BiConsumer<VacationRequestDTO, EmployeeDTO>> vacationActions;

    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository, EmployeeService employeeService, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
        this.employeeService = employeeService;

        this.vacationActions = Map.of(
                VacationStatus.APPROVED, this::acceptRequest,
                VacationStatus.REJECTED, this::rejectRequest
        );
    }


    @Override
    public VacationRequestDTO createVacationRequest(VacationRequestDTO vacationRequest, List<LocalDate> holidays) {

        LocalDate start = vacationRequest.getStartDate();
        LocalDate end   = vacationRequest.getEndDate();
        Long employeeId = vacationRequest.getEmployee().getId();
        Employee employee = vacationRequest.getEmployee();

        checkDates(employeeId, start, end);
        processingRequest(vacationRequest, holidays);

        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(vacationRequest)));
    }

    private void  checkDates(Long employee, LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Η ημερομηνία λήξης είναι πριν την ημερομηνία έναρξης!");
        }

        //αν εχουμε απο το query εστω και ενα count exception και ελεγχοσ status

        Integer result = vacationRequestRepository.countOfOverlappingRequests(employee, List.of(VacationStatus.PENDING , VacationStatus.APPROVED), startDate, endDate);
        if (result > 0) {
             throw new IllegalArgumentException();
        }
    }

    //Αφαιρει τισ μερες που ειναι αργιεσ η σαββατοκυριακα και κανει pending
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


    @Override
    public List<VacationRequestDTO> getAllVacationRequests() {
        return vacationRequestMapper.toDTO(vacationRequestRepository.findAll());
    }

    @Override
    public VacationRequestDTO getVacationRequestById(Long id) {
        return vacationRequestMapper.toDTO(vacationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public VacationRequestDTO updateVacationRequest(Long id, VacationRequestDTO vacationRequest) {

        VacationRequestDTO currentVacationRequest = getVacationRequestById(id);
        vacationRequestMapper.updateEntityFromDTO(vacationRequestMapper.toEntity(currentVacationRequest), vacationRequest);

        return vacationRequestMapper.toDTO(vacationRequestRepository.save(vacationRequestMapper.toEntity(currentVacationRequest)));
    }

    @Override
    public void deleteVacationRequest(Long id) {
        VacationRequestDTO vacationRequest = vacationRequestMapper.toDTO(vacationRequestRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        vacationRequestRepository.deleteById(id);
    }

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
    

    private void acceptRequest(VacationRequestDTO vacationRequest, EmployeeDTO employee){

        vacationRequest.setStatus(VacationStatus.APPROVED);
        employeeService.saveEmployee(employee);

    }

    private void rejectRequest(VacationRequestDTO vacationRequest, EmployeeDTO employee){

        int availableDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();

        employee.setVacationDays(availableDays += requestedDays);
        vacationRequest.setStatus(VacationStatus.REJECTED);
    }
}