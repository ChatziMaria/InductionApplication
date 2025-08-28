package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long> {
    List<EmployeeProduct> findByEmployeeId(Long id);

    @Query("""
     SELECT ep
     FROM EmployeeProduct ep
     JOIN FETCH ep.employee e
     JOIN FETCH e.company c
     JOIN FETCH ep.product p
     WHERE c.id = :companyId
     """)
    List<EmployeeProduct> findEmployeesAndProductsByCompanyId(@Param("companyId") Long companyId);


}
