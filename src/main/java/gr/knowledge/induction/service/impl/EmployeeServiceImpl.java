package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.repository.CompanyRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee,Long id){

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException());
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee){

        Employee currentEmployee = getEmployeeById(id);

        Employee result = new Employee();

        result.setId(currentEmployee.getId());
        result.setName(employee.getName());
        result.setSurname(employee.getSurname());
        result.setEmail(employee.getEmail());
        result.setStartDate(employee.getStartDate());
        result.setVacationDays(employee.getVacationDays());
        result.setSalary(employee.getSalary());
        result.setEmploymentType((employee.getEmploymentType()));
        result.setCompany(employee.getCompany());


        return employeeRepository.save(result);
    }

    @Override
    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });

        employeeRepository.deleteById(id);

    }

    @Override
    public BigDecimal calculateMonthlyExpenses(Long companyId){

        List<Employee> companyEmployees = employeeRepository.findByCompanyId(companyId);

       BigDecimal totalSalary = BigDecimal.valueOf(0);
       for (Employee employee : companyEmployees) {
           totalSalary.add(employee.getSalary());
       }
       return totalSalary;
    }

    @Override
    public List<Employee> returnEmployees(Long companyId){
        return employeeRepository.findByCompanyId(companyId);
    }

}
