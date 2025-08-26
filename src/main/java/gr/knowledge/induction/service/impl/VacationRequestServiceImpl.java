package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.domain.VacationStatus;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.repository.VacationRequestRepository;
import gr.knowledge.induction.service.EmployeeService;
import gr.knowledge.induction.service.VacationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final EmployeeService employeeService;

    private final Map<VacationStatus, BiConsumer<VacationRequest, Employee>> vacationActions;

    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository, EmployeeService employeeService) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.employeeService = employeeService;

        this.vacationActions = Map.of(
                VacationStatus.APPROVED, this::acceptRequest,
                VacationStatus.REJECTED, this::rejectRequest
        );
    }


    @Override
    public VacationRequest createVacationRequest(VacationRequest vacationRequest, List<LocalDate> holidays) {

        LocalDate start = vacationRequest.getStartDate();
        LocalDate end   = vacationRequest.getEndDate();
        Long employeeId = vacationRequest.getEmployee().getId();
        Employee employee = vacationRequest.getEmployee();

        checkDates(employeeId, start, end);
        processingRequest(vacationRequest, holidays);

        return vacationRequestRepository.save(vacationRequest);
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
    private void processingRequest(VacationRequest vacationRequest, List<LocalDate> holidays ){
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
    public List<VacationRequest> getAllVacationRequests() {
        return vacationRequestRepository.findAll();
    }

    @Override
    public VacationRequest getVacationRequestById(Long id) {
        return vacationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public VacationRequest updateVacationRequest(Long id, VacationRequest vacationRequest) {

        VacationRequest currentVacationRequest = getVacationRequestById(id);


        currentVacationRequest.setStartDate(vacationRequest.getEndDate());
        currentVacationRequest.setEndDate(vacationRequest.getEndDate());
        currentVacationRequest.setStatus(vacationRequest.getStatus());
        currentVacationRequest.setDays(vacationRequest.getDays());
        currentVacationRequest.setEmployee(vacationRequest.getEmployee());

        return vacationRequestRepository.save(currentVacationRequest);
    }

    @Override
    public void deleteVacationRequest(Long id) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });

        vacationRequestRepository.deleteById(id);
    }

    @Override
    public VacationRequest requestVacation(VacationRequest vacationRequest) {
        Long employeeId = vacationRequest.getEmployee().getId();

        Employee employee = employeeService.getEmployeeById(employeeId);

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
        return vacationRequestRepository.save(vacationRequest);
    }


    @Override
    public VacationRequest handleRequest(VacationRequest vacationRequest){

        VacationRequest currentVacationRequest = getVacationRequestById(vacationRequest.getId());
        Long employeeId = vacationRequest.getEmployee().getId();
        Employee employee = employeeService.getEmployeeById(employeeId);

        int availableDays = Optional.ofNullable(employee.getVacationDays())
                .orElseThrow(() -> new IllegalArgumentException());

        int requestedDays = Optional.ofNullable(vacationRequest.getDays())
                .orElseThrow(() -> new IllegalArgumentException());

        if (requestedDays <= 0) {
            throw new IllegalArgumentException();
        }

       VacationStatus status = (requestedDays <= availableDays) ? VacationStatus.APPROVED : VacationStatus.REJECTED;

        vacationActions.get(status).accept(vacationRequest, employee);
        return vacationRequestRepository.save(vacationRequest);
    }
    

    private void acceptRequest(VacationRequest vacationRequest, Employee employee){

        vacationRequest.setStatus(VacationStatus.APPROVED);
        employeeService.saveEmployee(employee);

    }

    private void rejectRequest(VacationRequest vacationRequest, Employee employee){

        int availableDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();

        employee.setVacationDays(availableDays += requestedDays);
        vacationRequest.setStatus(VacationStatus.REJECTED);
    }
}