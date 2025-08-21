package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.repository.CompanyRepository;
import gr.knowledge.induction.service.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {

        this.companyRepository = companyRepository;
    }


    @Override
   public Company createCompany(Company company){
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company){

        Company currentCompany = getCompanyById(id);

        currentCompany.setName(company.getName());
        currentCompany.setAddress(company.getAddress());
        currentCompany.setPhone(company.getPhone());



        return companyRepository.save(currentCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });

        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id));
    }

}
