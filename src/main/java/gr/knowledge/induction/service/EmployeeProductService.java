package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeProductService {

    EmployeeProduct createEmployeeProduct(EmployeeProduct employeeProduct, Long id);
    List<EmployeeProduct> getAllEmployeeProducts();
    EmployeeProduct getEmployeeProductById(Long id);
    EmployeeProduct updateEmployeeProduct(Long id, EmployeeProduct employeeProduct);
    void deleteEmployeeProduct(Long id);
    Map<String,List<EmployeeProduct>> getAllCompanyProducts(Long companyId);
}
