package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.EmployeeProductDTO;
import gr.knowledge.induction.service.EmployeeProductService;
import gr.knowledge.induction.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employeeProduct")
public class EmployeeProductController {

    private final EmployeeProductService employeeProductService;


    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    @PostMapping
    public ResponseEntity<EmployeeProductDTO> createEmployeeProduct(@RequestBody EmployeeProductDTO employeeProduct){
        EmployeeProductDTO createdEmployeeProduct = employeeProductService.createEmployeeProduct(employeeProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeProduct);
    };


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProductDTO> updateEmployeeProduct(@PathVariable Long id,@RequestBody EmployeeProductDTO employeeProduct){

        EmployeeProductDTO updatedEmployeeProduct = employeeProductService.updateEmployeeProduct(id,employeeProduct);
        return ResponseEntity.ok(updatedEmployeeProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeProductService.deleteEmployeeProduct(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<EmployeeProductDTO>> getAllEmployeeProducts(){
        List<EmployeeProductDTO> gotAllEmployeeProducts = employeeProductService.getAllEmployeeProducts();
        return ResponseEntity.ok(gotAllEmployeeProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProductDTO> getEmployeeProductById(@PathVariable Long id){
        EmployeeProductDTO employeeProduct = employeeProductService.getEmployeeProductById(id);
        return  ResponseEntity.ok(employeeProduct);
    }

    @GetMapping("/getALLCompanyProducts/{companyId}")
    public ResponseEntity<Map<String,List<Product>>> getAllCompanyProducts(@PathVariable Long companyId){
        Map<String, List<Product>> gotAllCompanyProducts = employeeProductService.getAllCompanyProducts(companyId);
        return  ResponseEntity.ok(gotAllCompanyProducts);

    }

}

