package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.BonusRate;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.mapper.BonusMapper;
import gr.knowledge.induction.repository.BonusRepository;
import gr.knowledge.induction.service.BonusService;
import gr.knowledge.induction.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;

    private final BonusMapper bonusMapper;

    private final EmployeeService employeeService;


    public BonusServiceImpl(BonusRepository bonusRepository, EmployeeService employeeService, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.employeeService = employeeService;
        this.bonusMapper = bonusMapper;
    }

    @Override
    public BonusDTO createBonus(BonusDTO bonusDTO) {
        return bonusMapper.toDTO(bonusRepository.save(bonusMapper.toEntity(bonusDTO)));
    }

    @Override
    public List<BonusDTO> getAllBonus() {
        return bonusMapper.toDTO(bonusRepository.findAll());
    }

    @Override
    public BonusDTO getBonusById(Long id) {
        return bonusMapper.toDTO(bonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

    @Override
    public BonusDTO updateBonus(Long id, BonusDTO updatedBonus) {

        BonusDTO currentBonus = getBonusById(id);
        bonusMapper.updateEntityFromDTO(bonusMapper.toEntity(currentBonus), updatedBonus);

        return bonusMapper.toDTO(bonusRepository.save(bonusMapper.toEntity(updatedBonus)));
    }

    @Override
    public void deleteBonus(Long id) {
        BonusDTO existingBonus = getBonusById(id);
        bonusRepository.delete(bonusMapper.toEntity(existingBonus));
    }

    @Override
    public BigDecimal bonusCalculation(String season, BigDecimal salary) {

        if (salary == null || season == null) {
            throw new IllegalArgumentException();
        }
        BonusRate bonusRate = BonusRate.getRateBySeason(season);
        return salary.multiply(BigDecimal.valueOf(bonusRate.getRate()));
    }

    @Override
    public List<BonusDTO> bonusesForCompany(Long companyId, String season){

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


