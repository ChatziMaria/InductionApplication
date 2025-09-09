package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.enums.BonusRate;
import gr.knowledge.induction.enums.Seasons;
import gr.knowledge.induction.mapper.BonusMapper;
import gr.knowledge.induction.repository.BonusRepository;
import gr.knowledge.induction.service.BonusService;
import gr.knowledge.induction.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Υλοποίηση της υπηρεσίας BonusService.
 * Παρέχει λειτουργίες δημιουργίας, ανάκτησης, ενημέρωσης, διαγραφής και υπολογισμού μπόνους
 * για εργαζόμενους και εταιρείες.
 */
@Service

public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;

    private final BonusMapper bonusMapper;

    private final EmployeeService employeeService;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param bonusRepository το repository για τα μπόνους
     * @param employeeService η υπηρεσία εργαζομένων για την ανάκτηση των υπαλλήλων μιας εταιρείας
     * @param bonusMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public BonusServiceImpl(BonusRepository bonusRepository, EmployeeService employeeService, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.employeeService = employeeService;
        this.bonusMapper = bonusMapper;
    }

    /**
     * Δημιουργεί ένα νέο μπόνους.
     *
     * @param bonusDTO το αντικείμενο DTO που περιγράφει το μπόνους
     * @return το δημιουργημένο BonusDTO
     */
    @Override
    public BonusDTO createBonus(BonusDTO bonusDTO) {
        return bonusMapper.toDTO(bonusRepository.save(bonusMapper.toEntity(bonusDTO)));
    }

    /**
     * Επιστρέφει όλα τα μπόνους.
     *
     * @return λίστα αντικειμένων BonusDTO
     */
    @Override
    public List<BonusDTO> getAllBonus() {
        return bonusMapper.toDTO(bonusRepository.findAll());
    }

    /**
     * Επιστρέφει ένα μπόνους με βάση το ID.
     *
     * @param id το ID του μπόνους
     * @return το αντικείμενο BonusDTO
     * @throws EntityNotFoundException αν δεν βρεθεί μπόνους με το συγκεκριμένο ID
     */

    @Override
    public BonusDTO getBonusById(Long id) {
        return bonusMapper.toDTO(bonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

    /**
     * Ενημερώνει ένα υπάρχον μπόνους με βάση το ID.
     *
     * @param id           το ID του μπόνους που θα ενημερωθεί
     * @param updatedBonus το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return το ενημερωμένο BonusDTO
     */
    @Override
    public BonusDTO updateBonus(Long id, BonusDTO updatedBonus) {

        BonusDTO currentBonus = getBonusById(id);
        bonusMapper.updateEntityFromDTO(bonusMapper.toEntity(currentBonus), updatedBonus);

        return bonusMapper.toDTO(bonusRepository.save(bonusMapper.toEntity(updatedBonus)));
    }

    /**
     * Διαγράφει ένα μπόνους με βάση το ID.
     *
     * @param id το ID του μπόνους που θα διαγραφεί
     */
    @Override
    public void deleteBonus(Long id) {
        BonusDTO existingBonus = getBonusById(id);
        bonusRepository.delete(bonusMapper.toEntity(existingBonus));
    }

    /**
     * Υπολογίζει το μπόνους ενός εργαζομένου με βάση τη σεζόν και τον μισθό.
     *
     * @param season η σεζόν για τον υπολογισμό του μπόνους
     * @param salary ο μισθός του εργαζομένου
     * @return το ποσό του μπόνους ως BigDecimal
     * @throws IllegalArgumentException αν ο μισθός ή η σεζόν είναι null
     */
    @Override
    public BigDecimal bonusCalculation(Seasons season, BigDecimal salary) {

        if (salary == null || season == null) {
            throw new IllegalArgumentException();
        }
        BonusRate bonusRate = BonusRate.getRateBySeason(season);
        return salary.multiply(BigDecimal.valueOf(bonusRate.getRate()));
    }

    /**
     * Υπολογίζει και αποθηκεύει μπόνους για όλους τους εργαζομένους μιας εταιρείας για συγκεκριμένη σεζόν.
     *
     * @param companyId το ID της εταιρείας
     * @param season    η σεζόν για τον υπολογισμό των μπόνους
     * @return λίστα με τα δημιουργημένα BonusDTO
     */
    @Override
    public List<BonusDTO> bonusesForCompany(Long companyId, Seasons season){

        List<EmployeeDTO> companyEmployees = employeeService.returnEmployees(companyId);

        List<BonusDTO> bonusesToSave = new ArrayList<>();

        for (EmployeeDTO employee : companyEmployees) {
            BigDecimal salary = employee.getSalary();
            BigDecimal calculateBonus = bonusCalculation(season, salary);

            BonusDTO bonus = new BonusDTO();
            bonus.setEmployee(employee);
            bonus.setCompany(employee.getCompany());
            bonus.setAmount(calculateBonus);

            bonusesToSave.add(bonus);
        }
        return bonusMapper.toDTO(bonusRepository.saveAll(bonusMapper.toEntity(bonusesToSave)));
    }
}


