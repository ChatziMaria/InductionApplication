package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface BonusService {

    Bonus createBonus(Bonus bonus, Long id);
    List<Bonus> getAllBonus();
    Optional<Bonus> getBonusById(Long id);
    Bonus updateBonus(Long id, Bonus bonus);
    void deleteBonus(Long id);
    double bonusCalculation(String season, Double salary);
    List<Bonus> bonusesForCompany(Long companyId, String season);

}
