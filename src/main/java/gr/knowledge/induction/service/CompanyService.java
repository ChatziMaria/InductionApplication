package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company createCompany(Company company);
    Company updateCompany(Long id, Company company);
    void deleteCompany(Long id);
    Company getCompanyById(Long Id);
    List<Company> getAllCompanies();

}


