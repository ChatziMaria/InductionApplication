package gr.knowledge.induction.web.rest;


import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.service.CompanyService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id,@RequestBody Company company){

        Company updatedCompany = companyService.updateCompany(id,company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id,@RequestBody Company company){
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(@RequestBody Company company){
        List<Company> gotAllCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok( gotAllCompanies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        return  ResponseEntity.ok(company);
    }




}
