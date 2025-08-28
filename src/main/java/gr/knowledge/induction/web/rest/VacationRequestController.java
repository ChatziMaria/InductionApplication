package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.dto.VacationRequestDTO;
import gr.knowledge.induction.service.VacationRequestService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vacationRequest")
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @PostMapping
    public  ResponseEntity<VacationRequestDTO> createVacationRequest(@RequestBody VacationRequestDTO vacationRequest,
                                                                     @RequestParam(name = "holidays", required = false)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> holidays){

        VacationRequestDTO createdVacationRequest = vacationRequestService.createVacationRequest(vacationRequest, holidays);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest( @PathVariable Long id,@RequestBody VacationRequestDTO vacationRequest){

        VacationRequestDTO updatedVacationRequest = vacationRequestService.updateVacationRequest(id,vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacationRequest(@PathVariable Long id){
        vacationRequestService.deleteVacationRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequests(){
        List<VacationRequestDTO> gotAllVacationRequests = vacationRequestService.getAllVacationRequests();
        return ResponseEntity.ok( gotAllVacationRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationRequestDTO> getVacationRequestById(@PathVariable Long id){
        VacationRequestDTO gotVacationRequestById = vacationRequestService.getVacationRequestById(id);
        return ResponseEntity.ok(gotVacationRequestById);
    }

    @PostMapping("/request")
    public ResponseEntity<VacationRequestDTO> requestVacation(@RequestBody VacationRequestDTO vacationRequest){
        VacationRequestDTO processedRequest = vacationRequestService.requestVacation(vacationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(processedRequest);
    }

    @PutMapping("/handleRequest")
    public  ResponseEntity<VacationRequestDTO> handleRequest(@RequestBody VacationRequestDTO vacationRequest){
        VacationRequestDTO updatedVacationRequest = vacationRequestService.handleRequest(vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }
}
