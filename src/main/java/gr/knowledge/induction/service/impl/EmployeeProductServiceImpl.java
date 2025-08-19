package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.repository.EmployeeProductRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.EmployeeProductService;
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
    public EmployeeProduct createEmployeeProduct(EmployeeProduct employeeProduct, Long id){
        return  employeeProductRepository.save(employeeProduct);
    }

    @Override
    public List<EmployeeProduct> getAllEmployeeProducts() {
        return employeeProductRepository.findAll();
    }

    @Override
    public Optional<EmployeeProduct> getEmployeeProductById(Long id) {
        return employeeProductRepository.findById(id);
    }

    @Override
    public EmployeeProduct updateEmployeeProduct(Long id, EmployeeProduct employeeProduct){
        EmployeeProduct result = new EmployeeProduct();
        Optional<EmployeeProduct> currentEmployeeProduct = getEmployeeProductById(id);

        if(currentEmployeeProduct.isPresent()){
            result.setId(currentEmployeeProduct.get().getId());
            result.setEmployee(employeeProduct.getEmployee());
            result.setProduct(employeeProduct.getProduct());
        }
        else{
            throw new RuntimeException();
        }

        return employeeProductRepository.save(employeeProduct);
    }

    @Override
    public void deleteEmployeeProduct(Long id){
        Optional<EmployeeProduct> employeeProduct = getEmployeeProductById(id);

        if (employeeProduct.isPresent()) {

            employeeProductRepository.deleteById(id);
        }
        else{
            throw new RuntimeException();
        }
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
