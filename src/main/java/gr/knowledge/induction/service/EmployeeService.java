package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.EmployeeDTO;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employee);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employee);

    void deleteEmployee(Long id);

    BigDecimal calculateMonthlyExpenses(Long companyId);

    List<EmployeeDTO> returnEmployees(Long companyId);

    EmployeeDTO saveEmployee(EmployeeDTO employee);
}
