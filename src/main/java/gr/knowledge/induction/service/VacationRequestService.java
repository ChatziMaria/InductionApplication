package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.VacationRequest;

import java.util.List;


public interface VacationRequestService {

    VacationRequest createVacationRequest(VacationRequest vacationRequest);
    List<VacationRequest> getAllVacationRequests();
    VacationRequest getVacationRequestById(Long id);
    VacationRequest updateVacationRequest(Long id, VacationRequest vacationRequest);
    void  deleteVacationRequest(Long id);
    VacationRequest requestVacation(VacationRequest vacationRequest);
    VacationRequest handleRequest(VacationRequest vacationRequest);

}
