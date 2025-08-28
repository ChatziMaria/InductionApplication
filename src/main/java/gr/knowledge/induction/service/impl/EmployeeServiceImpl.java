package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.mapper.EmployeeMapper;
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

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;

    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee){
        return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employee)));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.toDTO(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException()));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee){

        EmployeeDTO currentEmployee = getEmployeeById(id);
        employeeMapper.updateEntityFromDTO(employeeMapper.toEntity(currentEmployee), employee);

        return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(currentEmployee)));
    }

    @Override
    public void deleteEmployee(Long id){
        EmployeeDTO employee = employeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        employeeRepository.deleteById(id);

    }

    @Override
    public BigDecimal calculateMonthlyExpenses(Long companyId){

        List<EmployeeDTO> companyEmployees = employeeMapper.toDTO(employeeRepository.findByCompanyId(companyId));

       BigDecimal totalSalary = BigDecimal.valueOf(0);
       for (EmployeeDTO employee : companyEmployees) {
           totalSalary.add(employee.getSalary());
       }
       return totalSalary;
    }

    @Override
    public List<EmployeeDTO> returnEmployees(Long companyId){
        return employeeMapper.toDTO(employeeRepository.findByCompanyId(companyId));
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employee){
        return  employeeRepository.save(employee);
    }
}
