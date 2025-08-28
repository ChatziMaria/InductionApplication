package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.dto.CompanyDTO;
import gr.knowledge.induction.mapper.CompanyMapper;
import gr.knowledge.induction.repository.CompanyRepository;
import gr.knowledge.induction.service.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {

        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    @Override
   public CompanyDTO createCompany(CompanyDTO company){
        return companyMapper.toDTO(companyRepository.save(companyMapper.toEntity(company)));
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO company){

        CompanyDTO currentCompany = getCompanyById(id);
        companyMapper.updateEntityFromDTO(companyMapper.toEntity(currentCompany),company);

        return companyMapper.toDTO(companyRepository.save(companyMapper.toEntity(currentCompany)));
    }

    @Override
    public void deleteCompany(Long id) {
        CompanyDTO company = companyMapper.toDTO(companyRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        companyRepository.deleteById(id);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyMapper.toDTO(companyRepository.findAll());
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        return companyMapper.toDTO(companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

}
