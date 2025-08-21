package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.domain.VacationStatus;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.repository.VacationRequestRepository;
import gr.knowledge.induction.service.VacationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public VacationRequest createVacationRequest(VacationRequest vacationRequest, Long Id) {

        return vacationRequestRepository.save(vacationRequest);
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

        Employee employee =employeeRepository.findById(employeeId)
                .orElseThrow();

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
    public VacationRequest AcceptOrReject(VacationRequest vacationRequest){

        VacationRequest currentVacationRequest = getVacationRequestById(vacationRequest.getId());

        Long employeeId = vacationRequest.getEmployee().getId();
        Employee employee =employeeRepository.findById(employeeId)
                .orElseThrow();


        int availableDays = employee.getVacationDays();
        int requestedDays = vacationRequest.getDays();


        if(requestedDays <= availableDays){
            availableDays -= requestedDays;
            vacationRequest.setStatus(VacationStatus.APPROVED);
            return  vacationRequestRepository.save(vacationRequest);
        }
        else{
            vacationRequest.setStatus(VacationStatus.REJECTED);
            return vacationRequestRepository.save(vacationRequest);
        }
    }
}