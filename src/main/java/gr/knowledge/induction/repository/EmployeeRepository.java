package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByCompanyId(Long companyId);
}
