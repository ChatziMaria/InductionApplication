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

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;

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


    //TODO: να προστεθει λογικη δεσμευσης των ημερων του ζητούμενου vacation Request καθως και λογικη ελεγχου επικαλυψης
    //TODO: με άλλα pending vacation request του employee.
    @Override
    public VacationRequest createVacationRequest(VacationRequest vacationRequest, Integer holiday) {

        LocalDate start = vacationRequest.getStartDate();
        LocalDate end   = vacationRequest.getEndDate();
        Long employeeId = vacationRequest.getEmployee().getId();
        VacationStatus status = vacationRequest.getStatus();

        checkDates(employeeId, Collections.singletonList(status), start,end);
        vacationRequest.setStatus(VacationStatus.PENDING);

        return vacationRequestRepository.save(vacationRequest);
    }

    private void  checkDates(Long employee,List<VacationStatus> vacationStatus, LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Η ημερομηνία λήξης είναι πριν την ημερομηνία έναρξης!");
        }

        //αν εχουμε απο το query εστω και ενα count exception και μετα ελεγχοσ status
        if (vacationRequestRepository.countOfOverlappingRequests(employee, vacationStatus, startDate, endDate) > 0) {

             if (vacationStatus.contains(VacationStatus.APPROVED ) || vacationStatus.contains(VacationStatus.PENDING)) {
                 throw new IllegalArgumentException();
             }

        }
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

        int availableDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();

        employee.setVacationDays(availableDays -= requestedDays);
        vacationRequest.setStatus(VacationStatus.APPROVED);
        employeeService.saveEmployee(employee);

    }


    //TODO: να προστεθει λογικη αποδεσμευσης ημερων σε περιπτωση απορριψης
    private void rejectRequest(VacationRequest vacationRequest, Employee employee){

        vacationRequest.setStatus(VacationStatus.REJECTED);
    }
}