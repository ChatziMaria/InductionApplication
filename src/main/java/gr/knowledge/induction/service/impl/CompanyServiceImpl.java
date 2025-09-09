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

/**
 * Υλοποίηση της υπηρεσίας CompanyService.
 * Παρέχει λειτουργίες δημιουργίας, ενημέρωσης, διαγραφής και ανάκτησης εταιρειών.
 */
@Service
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param companyRepository το repository για τις εταιρείες
     * @param companyMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {

        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Δημιουργεί μια νέα εταιρεία.
     *
     * @param company το αντικείμενο DTO που περιγράφει την εταιρεία
     * @return το δημιουργημένο CompanyDTO
     */
    @Override
    public CompanyDTO createCompany(CompanyDTO company){
        return companyMapper.toDTO(companyRepository.save(companyMapper.toEntity(company)));
    }

    /**
     * Ενημερώνει μια υπάρχουσα εταιρεία με βάση το ID.
     *
     * @param id      το ID της εταιρείας που θα ενημερωθεί
     * @param company το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return το ενημερωμένο CompanyDTO
     */
    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO company){

        CompanyDTO currentCompany = getCompanyById(id);
        companyMapper.updateEntityFromDTO(companyMapper.toEntity(currentCompany),company);

        return companyMapper.toDTO(companyRepository.save(companyMapper.toEntity(currentCompany)));
    }

    /**
     * Διαγράφει μια εταιρεία με βάση το ID.
     *
     * @param id το ID της εταιρείας που θα διαγραφεί
     * @throws EntityNotFoundException αν δεν βρεθεί εταιρεία με το συγκεκριμένο ID
     */
    @Override
    public void deleteCompany(Long id) {
        CompanyDTO company = companyMapper.toDTO(companyRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        companyRepository.deleteById(id);
    }

    /**
     * Επιστρέφει όλες τις εταιρείες.
     *
     * @return λίστα αντικειμένων CompanyDTO
     */
    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyMapper.toDTO(companyRepository.findAll());
    }

    /**
     * Επιστρέφει μια εταιρεία με βάση το ID.
     *
     * @param id το ID της εταιρείας
     * @return το αντικείμενο CompanyDTO
     * @throws EntityNotFoundException αν δεν βρεθεί εταιρεία με το συγκεκριμένο ID
     */
    @Override
    public CompanyDTO getCompanyById(Long id) {
        return companyMapper.toDTO(companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

}
