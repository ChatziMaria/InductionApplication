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

/**
 * REST Controller για τη διαχείριση αιτήσεων άδειας.
 * Παρέχει endpoints για δημιουργία, ενημέρωση, διαγραφή, ανάκτηση και επεξεργασία αιτήσεων.
 */
@RestController
@RequestMapping("/api/vacationRequest")
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    /**
     * Κατασκευαστής για την έγχυση του VacationRequestService.
     *
     * @param vacationRequestService η υπηρεσία που διαχειρίζεται τις αιτήσεις άδειας
     */
    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    /**
     * Δημιουργεί μια νέα αίτηση άδειας.
     *
     * @param vacationRequest το αντικείμενο DTO της αίτησης άδειας
     * @param holidays       προαιρετική λίστα ημερομηνιών αργιών που επηρεάζουν την αίτηση
     * @return ResponseEntity με το δημιουργημένο αντικείμενο VacationRequestDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public  ResponseEntity<VacationRequestDTO> createVacationRequest(@RequestBody VacationRequestDTO vacationRequest,
                                                                     @RequestParam(name = "holidays", required = false)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> holidays){

        VacationRequestDTO createdVacationRequest = vacationRequestService.createVacationRequest(vacationRequest, holidays);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacationRequest);
    }

    /**
     * Ενημερώνει μια υπάρχουσα αίτηση άδειας με βάση το ID.
     *
     * @param id              το ID της αίτησης που θα ενημερωθεί
     * @param vacationRequest το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο VacationRequestDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest( @PathVariable Long id,@RequestBody VacationRequestDTO vacationRequest){

        VacationRequestDTO updatedVacationRequest = vacationRequestService.updateVacationRequest(id,vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }

    /**
     * Διαγράφει μια αίτηση άδειας με βάση το ID.
     *
     * @param id το ID της αίτησης που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVacationRequest(@PathVariable Long id){
        vacationRequestService.deleteVacationRequest(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Επιστρέφει όλες τις αιτήσεις άδειας.
     *
     * @return ResponseEntity με λίστα αντικειμένων VacationRequestDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequests(){
        List<VacationRequestDTO> gotAllVacationRequests = vacationRequestService.getAllVacationRequests();
        return ResponseEntity.ok( gotAllVacationRequests);
    }

    /**
     * Επιστρέφει μια αίτηση άδειας με βάση το ID.
     *
     * @param id το ID της αίτησης
     * @return ResponseEntity με το αντικείμενο VacationRequestDTO
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<VacationRequestDTO> getVacationRequestById(@PathVariable Long id){
        VacationRequestDTO gotVacationRequestById = vacationRequestService.getVacationRequestById(id);
        return ResponseEntity.ok(gotVacationRequestById);
    }


    /**
     * Υποβάλλει αίτηση άδειας για επεξεργασία.
     *
     * @param vacationRequest το αντικείμενο DTO της αίτησης άδειας
     * @return ResponseEntity με το επεξεργασμένο αντικείμενο VacationRequestDTO και HTTP status 201 (Created)
     */
    @PostMapping("/request")
    public ResponseEntity<VacationRequestDTO> requestVacation(@RequestBody VacationRequestDTO vacationRequest){
        VacationRequestDTO processedRequest = vacationRequestService.requestVacation(vacationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(processedRequest);
    }

    /**
     * Επεξεργάζεται μια υπάρχουσα αίτηση άδειας (π.χ. έγκριση ή απόρριψη).
     *
     * @param vacationRequest το αντικείμενο DTO της αίτησης άδειας
     * @return ResponseEntity με το ενημερωμένο αντικείμενο VacationRequestDTO
     */
    @PutMapping("/handleRequest")
    public  ResponseEntity<VacationRequestDTO> handleRequest(@RequestBody VacationRequestDTO vacationRequest){
        VacationRequestDTO updatedVacationRequest = vacationRequestService.handleRequest(vacationRequest);
        return ResponseEntity.ok(updatedVacationRequest);
    }
}
