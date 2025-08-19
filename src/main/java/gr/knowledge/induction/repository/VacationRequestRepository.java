package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
}
