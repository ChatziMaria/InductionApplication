package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BonusService {

    Bonus createBonus(Bonus bonus);

    List<Bonus> getAllBonus();

    Bonus getBonusById(Long id);

    Bonus updateBonus(Long id, Bonus bonus);

    void deleteBonus(Long id);

    BigDecimal bonusCalculation(String season, BigDecimal salary);

    List<Bonus> bonusesForCompany(Long companyId, String season);

}
