package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.enums.Seasons;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BonusService {

    BonusDTO createBonus(BonusDTO bonus);

    List<BonusDTO> getAllBonus();

    BonusDTO getBonusById(Long id);

    BonusDTO updateBonus(Long id, BonusDTO bonus);

    void deleteBonus(Long id);

    BigDecimal bonusCalculation(Seasons season, BigDecimal salary);

    List<BonusDTO> bonusesForCompany(Long companyId, Seasons season);

}
