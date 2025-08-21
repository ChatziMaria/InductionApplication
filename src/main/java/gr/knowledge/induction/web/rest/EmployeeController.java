package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee, Long id){
        Employee createdEmployee = employeeService.createEmployee(employee, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee){

        Employee updatedEmployee = employeeService.updateEmployee(id,employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id,@RequestBody Employee employee){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestBody Employee employee){
        List<Employee> gotAllEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok( gotAllEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return  ResponseEntity.ok(employee);
    }

    @GetMapping("/monthlyExpenses/{companyId}")
    public BigDecimal calculateMonthlyExpenses(@PathVariable Long companyId){
        return employeeService.calculateMonthlyExpenses(companyId);

    }
}
