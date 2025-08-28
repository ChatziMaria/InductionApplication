package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.dto.VacationRequestDTO;

import java.time.LocalDate;
import java.util.List;


public interface VacationRequestService {

    VacationRequestDTO createVacationRequest(VacationRequestDTO vacationRequest, List<LocalDate> holiday);
    List<VacationRequestDTO> getAllVacationRequests();
    VacationRequestDTO getVacationRequestById(Long id);
    VacationRequestDTO updateVacationRequest(Long id, VacationRequestDTO vacationRequest);
    void  deleteVacationRequest(Long id);
    VacationRequestDTO requestVacation(VacationRequestDTO vacationRequest);
    VacationRequestDTO handleRequest(VacationRequestDTO vacationRequest);

}
