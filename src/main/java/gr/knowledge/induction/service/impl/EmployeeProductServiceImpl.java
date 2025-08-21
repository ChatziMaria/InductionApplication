package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.repository.EmployeeProductRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.EmployeeProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeProductServiceImpl implements EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeProductServiceImpl(EmployeeProductRepository employeeProductRepository) {
        this.employeeProductRepository = employeeProductRepository;
    }

    @Override
    public EmployeeProduct createEmployeeProduct(EmployeeProduct employeeProduct){
        return  employeeProductRepository.save(employeeProduct);
    }

    @Override
    public List<EmployeeProduct> getAllEmployeeProducts() {
        return employeeProductRepository.findAll();
    }

    @Override
    public EmployeeProduct getEmployeeProductById(Long id) {

        return employeeProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public EmployeeProduct updateEmployeeProduct(Long id, EmployeeProduct employeeProduct){

        EmployeeProduct currentEmployeeProduct = getEmployeeProductById(id);


        currentEmployeeProduct.setEmployee(employeeProduct.getEmployee());
        currentEmployeeProduct.setProduct(employeeProduct.getProduct());

        return employeeProductRepository.save(employeeProduct);
    }

    @Override
    public void deleteEmployeeProduct(Long id){
        EmployeeProduct employeeProduct = employeeProductRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });
        employeeProductRepository.deleteById(id);

    }

    @Override
    public Map<String,List<EmployeeProduct>> getAllCompanyProducts(Long companyId){
        Map<String,List<EmployeeProduct>> employeeProductMap = new HashMap<>();

        List<Employee> companyEmployees = employeeRepository.findByCompanyId(companyId);

        for (Employee employee : companyEmployees) {

            List<EmployeeProduct> employeeProducts =  employeeProductRepository.findByEmployeeId(employee.getId());

            if (employeeProducts == null || employeeProducts.isEmpty()) {
                continue;
            }

            String fullName = employee.getName()+ " " + employee.getSurname();

            employeeProductMap.put(fullName, employeeProducts);
        }

        return employeeProductMap;
    }

}
