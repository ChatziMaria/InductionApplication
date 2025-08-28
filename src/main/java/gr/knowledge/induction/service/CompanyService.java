package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.dto.CompanyDTO;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    CompanyDTO createCompany(CompanyDTO company);
    CompanyDTO updateCompany(Long id, CompanyDTO company);
    void deleteCompany(Long id);
    CompanyDTO getCompanyById(Long Id);
    List<CompanyDTO> getAllCompanies();

}


