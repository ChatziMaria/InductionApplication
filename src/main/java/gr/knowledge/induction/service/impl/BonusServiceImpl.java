package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.BonusRate;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.repository.BonusRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gr.knowledge.induction.domain.BonusRate.WINTER;

@Service
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BonusServiceImpl(BonusRepository bonusRepository){
        this.bonusRepository = bonusRepository;
    }

    @Override
    public Bonus createBonus(Bonus bonus, Long id){

        return bonusRepository.save(bonus);
    }

    @Override
    public List<Bonus> getAllBonus() {
        return bonusRepository.findAll();
    }

    @Override
    public Optional<Bonus> getBonusById(Long id) {
        return bonusRepository.findById(id);
    }

    @Override
    public Bonus updateBonus(Long id, Bonus bonus){
        Bonus result = new Bonus();
        Optional<Bonus> currentBonus = getBonusById(id);

        if(currentBonus.isPresent() ){
            result.setId(currentBonus.get().getId());
            result.setAmount(currentBonus.get().getAmount());
            result.setCompany(currentBonus.get().getCompany());
            result.setEmployee(currentBonus.get().getEmployee());
        }
        else{
            throw new RuntimeException();
        }

        return bonusRepository.save(result);
    }

    @Override
    public void deleteBonus(Long id){
        Optional<Bonus> bonus = getBonusById(id);

        if(bonus.isPresent()){
            bonusRepository.deleteById(id);
        }
        else{ throw new RuntimeException();
        }

    }



    @Override
    public double bonusCalculation(String season,Double salary) {

        if (salary == null || season == null) {
            throw new IllegalArgumentException();
        }

        BonusRate bonusRate;
        double rate = switch (season) {
            case "WINTER" -> BonusRate.WINTER.getRate();
            case "AUTUMN" -> BonusRate.AUTUMN.getRate();
            case "SPRING" -> BonusRate.SPRING.getRate();
            case "SUMMER" -> BonusRate.SUMMER.getRate();
            default -> throw new RuntimeException();
        };


        return salary * rate;
    }

    @Override
    public List<Bonus> bonusesForCompany(Long companyId, String season){
        List<Employee> companyEmployees = employeeRepository.findByCompanyId(companyId);

        List<Bonus> bonusesToSave = new ArrayList<>();
        BonusRate bonusRate;


        for (Employee employee : companyEmployees) {
            double salary = employee.getSalary();
            double calculateBonus = bonusCalculation(season, salary);
            int amount = (int) (calculateBonus);

            Bonus bonus = new Bonus();
            bonus.setEmployee(employee);
            bonus.setCompany(employee.getCompany());
            bonus.setAmount(amount);


            bonusesToSave.add(bonus);

        }

        return bonusRepository.saveAll(bonusesToSave);
    }
}


