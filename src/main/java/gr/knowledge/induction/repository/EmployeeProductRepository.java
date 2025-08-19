package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long> {
    List<EmployeeProduct> findByEmployeeId(Long id);
}
