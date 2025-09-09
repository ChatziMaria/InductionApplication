package gr.knowledge.induction.web.rest;


import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.dto.CompanyDTO;
import gr.knowledge.induction.service.CompanyService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller για τη διαχείριση εταιρειών.
 * Παρέχει endpoints για δημιουργία, ενημέρωση, διαγραφή και ανάκτηση εταιρειών.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;


    /**
     * Κατασκευαστής για την έγχυση του CompanyService.
     *
     * @param companyService η υπηρεσία που διαχειρίζεται τις εταιρείες
     */
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Δημιουργεί μια νέα εταιρεία.
     *
     * @param company το αντικείμενο DTO που περιγράφει την εταιρεία
     * @return ResponseEntity με το δημιουργημένο αντικείμενο CompanyDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO company){
        CompanyDTO createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    /**
     * Ενημερώνει μια υπάρχουσα εταιρεία με βάση το ID.
     *
     * @param id      το ID της εταιρείας που θα ενημερωθεί
     * @param company το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο CompanyDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,@RequestBody CompanyDTO company){
        CompanyDTO updatedCompany = companyService.updateCompany(id,company);
        return ResponseEntity.ok(updatedCompany);
    }

    /**
     * Διαγράφει μια εταιρεία με βάση το ID.
     *
     * @param id το ID της εταιρείας που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Επιστρέφει όλες τις εταιρείες.
     *
     * @return ResponseEntity με λίστα αντικειμένων CompanyDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        List<CompanyDTO> gotAllCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok( gotAllCompanies);
    }

    /**
     * Επιστρέφει μια εταιρεία με βάση το ID.
     *
     * @param id το ID της εταιρείας
     * @return ResponseEntity με το αντικείμενο CompanyDTO
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id){
        CompanyDTO company = companyService.getCompanyById(id);
        return  ResponseEntity.ok(company);
    }




}
