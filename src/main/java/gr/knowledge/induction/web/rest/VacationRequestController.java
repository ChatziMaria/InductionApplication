package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.domain.VacationStatus;
import gr.knowledge.induction.service.VacationRequestService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacationRequest")
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @PostMapping
    public  ResponseEntity<VacationRequest> createVacationRequest(@RequestBody VacationRequest vacationRequest, Long id){

        VacationRequest createdVacationRequest = vacationRequestService.createVacationRequest(vacationRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacationRequest> updateVacationRequest( @PathVariable Long id,@RequestBody VacationRequest vacationRequest){

        VacationRequest updatedVacationRequest = vacationRequestService.updateVacationRequest(id,vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacationRequest(@PathVariable Long id, @RequestBody VacationRequest vacationRequest){
        vacationRequestService.deleteVacationRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VacationRequest>> getAllVacationRequests(@RequestBody VacationRequest vacationRequest){
        List<VacationRequest> gotAllVacationRequests = vacationRequestService.getAllVacationRequests();
        return ResponseEntity.ok( gotAllVacationRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationRequest> getVacationRequestById(@PathVariable Long id){
        VacationRequest gotVacationRequestById = vacationRequestService.getVacationRequestById(id);
        return ResponseEntity.ok(gotVacationRequestById);
    }

    @PostMapping("/request")
    public ResponseEntity<VacationRequest> requestVacation(@RequestBody VacationRequest vacationRequest){
        VacationRequest processedRequest = vacationRequestService.requestVacation(vacationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(processedRequest);
    }

    @PutMapping("/aOrR")
    public ResponseEntity<VacationRequest> AcceptOrReject(@RequestBody VacationRequest vacationRequest){
        VacationRequest updatedVacationRequest = vacationRequestService.AcceptOrReject(vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }
}
