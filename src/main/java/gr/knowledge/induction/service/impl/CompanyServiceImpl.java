package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.repository.CompanyRepository;
import gr.knowledge.induction.service.CompanyService;
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
        Company result = new Company();
        Optional<Company> currentCompany = getCompanyById(id);

        if(currentCompany.isPresent()){
            result.setId(currentCompany.get().getId());
            result.setName(company.getName());
            result.setAddress(company.getAddress());
            result.setPhone(company.getPhone());

        }else {
            throw new RuntimeException();
        }

        return companyRepository.save(result);
    }

    @Override
    public void deleteCompany(Long id){
        Optional<Company> company = getCompanyById(id);
        if(company.isPresent()) {
          companyRepository.deleteById(id);
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

}
