package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee createEmployee(Employee employee, Long companyId);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    BigDecimal calculateMonthlyExpenses(Long companyId);

    List<Employee> returnEmployees(Long companyId);
}
