package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.EmployeeProductDTO;
import gr.knowledge.induction.dto.ProductDTO;
import gr.knowledge.induction.service.EmployeeProductService;
import gr.knowledge.induction.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller για τη διαχείριση σχέσεων εργαζομένων και προϊόντων.
 * Παρέχει endpoints για δημιουργία, ενημέρωση, διαγραφή, ανάκτηση και εμφάνιση προϊόντων εταιρειών.
 */
@RestController
@RequestMapping("/api/employeeProduct")
public class EmployeeProductController {

    private final EmployeeProductService employeeProductService;

    /**
     * Κατασκευαστής για την έγχυση του EmployeeProductService.
     *
     * @param employeeProductService η υπηρεσία που διαχειρίζεται τις σχέσεις εργαζομένων-προϊόντων
     */
    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    /**
     * Δημιουργεί μια νέα σχέση εργαζομένου με προϊόν.
     *
     * @param employeeProduct το αντικείμενο DTO που περιγράφει τη σχέση εργαζομένου-προϊόντος
     * @return ResponseEntity με το δημιουργημένο αντικείμενο EmployeeProductDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<EmployeeProductDTO> createEmployeeProduct(@RequestBody EmployeeProductDTO employeeProduct){
        EmployeeProductDTO createdEmployeeProduct = employeeProductService.createEmployeeProduct(employeeProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeProduct);
    };


    /**
     * Ενημερώνει μια υπάρχουσα σχέση εργαζομένου με προϊόν με βάση το ID.
     *
     * @param id              το ID της σχέσης που θα ενημερωθεί
     * @param employeeProduct το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο EmployeeProductDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeProductDTO> updateEmployeeProduct(@PathVariable Long id,@RequestBody EmployeeProductDTO employeeProduct){

        EmployeeProductDTO updatedEmployeeProduct = employeeProductService.updateEmployeeProduct(id,employeeProduct);
        return ResponseEntity.ok(updatedEmployeeProduct);
    }


    /**
     * Διαγράφει μια σχέση εργαζομένου με προϊόν με βάση το ID.
     *
     * @param id το ID της σχέσης που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeProductService.deleteEmployeeProduct(id);
        return ResponseEntity.noContent().build();

    }

    /**
     * Επιστρέφει όλες τις σχέσεις εργαζομένων με προϊόντα.
     *
     * @return ResponseEntity με λίστα αντικειμένων EmployeeProductDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeProductDTO>> getAllEmployeeProducts(){
        List<EmployeeProductDTO> gotAllEmployeeProducts = employeeProductService.getAllEmployeeProducts();
        return ResponseEntity.ok(gotAllEmployeeProducts);
    }

    /**
     * Επιστρέφει μια σχέση εργαζομένου με προϊόν με βάση το ID.
     *
     * @param id το ID της σχέσης
     * @return ResponseEntity με το αντικείμενο EmployeeProductDTO
     */
    @GetMapping("getById/{id}")
    public ResponseEntity<EmployeeProductDTO> getEmployeeProductById(@PathVariable Long id){
        EmployeeProductDTO employeeProduct = employeeProductService.getEmployeeProductById(id);
        return  ResponseEntity.ok(employeeProduct);
    }


    /**
     * Επιστρέφει όλα τα προϊόντα μιας συγκεκριμένης εταιρείας ομαδοποιημένα κατά κατηγορία ή τύπο.
     *
     * @param companyId το ID της εταιρείας
     * @return ResponseEntity με έναν χάρτη (Map) όπου το κλειδί είναι η κατηγορία/τύπος και η τιμή λίστα προϊόντων (ProductDTO)
     */
    @GetMapping("/getALLCompanyProducts/{companyId}")
    public ResponseEntity<Map<String,List<ProductDTO>>> getAllCompanyProducts(@PathVariable Long companyId){
        Map<String, List<ProductDTO>> gotAllCompanyProducts = employeeProductService.getAllCompanyProducts(companyId);
        return  ResponseEntity.ok(gotAllCompanyProducts);

    }

}

