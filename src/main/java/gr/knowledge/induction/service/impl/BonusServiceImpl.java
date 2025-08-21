package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.BonusRate;
import gr.knowledge.induction.domain.Employee;
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

    private final EmployeeService employeeService;


    public BonusServiceImpl(BonusRepository bonusRepository, EmployeeService employeeService) {
        this.bonusRepository = bonusRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Bonus createBonus(Bonus bonus) {
        return bonusRepository.save(bonus);
    }

    @Override
    public List<Bonus> getAllBonus() {
        return bonusRepository.findAll();
    }

    @Override
    public Bonus getBonusById(Long id) {
        return bonusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id));
    }

    @Override
    public Bonus updateBonus(Long id, Bonus updatedBonus) {

        Bonus currentBonus = getBonusById(id);

         currentBonus.setAmount(updatedBonus.getAmount());
         currentBonus.setCompany(updatedBonus.getCompany());
         currentBonus.setEmployee(updatedBonus.getEmployee());

        return bonusRepository.save(currentBonus);
    }

    @Override
    public void deleteBonus(Long id) {
        Bonus existingBonus = bonusRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });
        bonusRepository.delete(existingBonus);
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
    public List<Bonus> bonusesForCompany(Long companyId, String season){
//        List<Employee> companyEmployees = employeeRepository.findByCompanyId(companyId);
        List<Employee> companyEmployees = employeeService.returnEmployees(companyId);

        List<Bonus> bonusesToSave = new ArrayList<>();

        for (Employee employee : companyEmployees) {
            BigDecimal salary = employee.getSalary();
            BigDecimal calculateBonus = bonusCalculation(season, salary);

            Bonus bonus = new Bonus();
            bonus.setEmployee(employee);
            bonus.setCompany(employee.getCompany());
            bonus.setAmount(calculateBonus);

            bonusesToSave.add(bonus);
        }
        return bonusRepository.saveAll(bonusesToSave);
    }
}


