package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface VacationRequestService {

    VacationRequest createVacationRequest(VacationRequest vacationRequest);
    List<VacationRequest> getAllVacationRequests();
    VacationRequest getVacationRequestById(Long id);
    VacationRequest updateVacationRequest(Long id, VacationRequest vacationRequest);
    void  deleteVacationRequest(Long id);
    VacationRequest requestVacation(VacationRequest vacationRequest);
    VacationRequest AcceptOrReject(VacationRequest vacationRequest);
}
