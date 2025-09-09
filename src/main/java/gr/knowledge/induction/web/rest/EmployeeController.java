package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller για τη διαχείριση εργαζομένων.
 * Παρέχει endpoints για δημιουργία, ενημέρωση, διαγραφή, ανάκτηση εργαζομένων
 * και υπολογισμό μηνιαίων εξόδων ανά εταιρεία.
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Κατασκευαστής για την έγχυση του EmployeeService.
     *
     * @param employeeService η υπηρεσία που διαχειρίζεται τους εργαζόμενους
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Δημιουργεί έναν νέο εργαζόμενο.
     *
     * @param employee το αντικείμενο DTO που περιγράφει τον εργαζόμενο
     * @return ResponseEntity με το δημιουργημένο αντικείμενο EmployeeDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employee){
        EmployeeDTO createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * Ενημερώνει έναν υπάρχοντα εργαζόμενο με βάση το ID.
     *
     * @param id       το ID του εργαζομένου που θα ενημερωθεί
     * @param employee το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο EmployeeDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDTO employee){
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id,employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Διαγράφει έναν εργαζόμενο με βάση το ID.
     *
     * @param id το ID του εργαζομένου που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();

    }


    /**
     * Επιστρέφει όλους τους εργαζομένους.
     *
     * @return ResponseEntity με λίστα αντικειμένων EmployeeDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> gotAllEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok( gotAllEmployees);
    }

    /**
     * Επιστρέφει έναν εργαζόμενο με βάση το ID.
     *
     * @param id το ID του εργαζομένου
     * @return ResponseEntity με το αντικείμενο EmployeeDTO
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return  ResponseEntity.ok(employee);
    }

    /**
     * Υπολογίζει τα συνολικά μηνιαία έξοδα για τους εργαζομένους μιας εταιρείας.
     *
     * @param companyId το ID της εταιρείας
     * @return BigDecimal που αντιπροσωπεύει τα μηνιαία έξοδα μισθοδοσίας
     */
    @GetMapping("/monthlyExpenses/{companyId}")
    public BigDecimal calculateMonthlyExpenses(@PathVariable Long companyId){
        return employeeService.calculateMonthlyExpenses(companyId);

    }
}
