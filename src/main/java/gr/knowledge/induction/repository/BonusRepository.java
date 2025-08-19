package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    List<Bonus> findByCompanyId(Long companyId);
}
