package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.EmployeeProductDTO;
import gr.knowledge.induction.dto.ProductDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeProductService {

    EmployeeProductDTO createEmployeeProduct(EmployeeProductDTO employeeProduct);
    List<EmployeeProductDTO> getAllEmployeeProducts();
    EmployeeProductDTO getEmployeeProductById(Long id);
    EmployeeProductDTO updateEmployeeProduct(Long id, EmployeeProductDTO employeeProduct);
    void deleteEmployeeProduct(Long id);
    Map<String,List<ProductDTO>> getAllCompanyProducts(Long companyId);
}
