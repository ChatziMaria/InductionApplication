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

    @Query("SELECT new com.example.demo.EmployeeDTO(e.firstName, e.lastName, p)" +
            " FROM employeeProduct ep  +" +
            " JOIN employee e ON e.id = ep.employee_id  +" +
            " JOIN company c ON c.id = e.company_id " +
            " WHERE c.id = :companyId")
    List<EmployeeProduct.EmployeeProductDTO> findEmployeesAndProductsByCompanyId(@Param(("companyId")) Long companyId);

}
