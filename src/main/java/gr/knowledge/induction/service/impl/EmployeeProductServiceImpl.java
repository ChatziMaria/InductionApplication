package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.EmployeeProductDTO;
import gr.knowledge.induction.dto.ProductDTO;
import gr.knowledge.induction.mapper.EmployeeProductMapper;
import gr.knowledge.induction.repository.EmployeeProductRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.EmployeeProductService;
import gr.knowledge.induction.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeProductServiceImpl implements EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    private final EmployeeProductMapper employeeProductMapper;

    private  final ProductService productService;

    public EmployeeProductServiceImpl(EmployeeProductRepository employeeProductRepository, ProductService productService, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.productService = productService;
        this.employeeProductMapper = employeeProductMapper;
    }

    @Override
    public EmployeeProductDTO createEmployeeProduct(EmployeeProductDTO employeeProduct){
        return  employeeProductMapper.toDTO(employeeProductRepository.save(employeeProductMapper.toEntity(employeeProduct)));
    }

    @Override
    public List<EmployeeProductDTO> getAllEmployeeProducts() {
        return employeeProductMapper.toDTO(employeeProductRepository.findAll());
    }

    @Override
    public EmployeeProductDTO getEmployeeProductById(Long id) {

        return employeeProductMapper.toDTO(employeeProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public EmployeeProductDTO updateEmployeeProduct(Long id, EmployeeProductDTO employeeProduct){

        EmployeeProductDTO currentEmployeeProduct = getEmployeeProductById(id);

        employeeProductMapper.updateEntityFromDTO(employeeProductMapper.toEntity(currentEmployeeProduct), employeeProduct );

        return employeeProductMapper.toDTO(employeeProductRepository.save(employeeProductMapper.toEntity(employeeProduct)));
    }

    @Override
    public void deleteEmployeeProduct(Long id){
        EmployeeProductDTO employeeProduct = employeeProductMapper.toDTO(employeeProductRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));
        employeeProductRepository.deleteById(id);

    }

    @Override
    public Map<String,List<ProductDTO>> getAllCompanyProducts(Long companyId){

       List<EmployeeProductDTO> employeeProducts = employeeProductMapper.toDTO(employeeProductRepository.findEmployeesAndProductsByCompanyId(companyId));

       Map<String,List<ProductDTO>> result = groupProductsByEmployee(employeeProducts);

        return result;
    }


    private Map<String,List<ProductDTO>> groupProductsByEmployee(List<EmployeeProductDTO> employeeProducts){

        Map<String,List<ProductDTO>> result = new HashMap<>();

        for(EmployeeProductDTO employeeProduct: employeeProducts){
            String fullName = employeeProduct.getEmployee().getName() + " " + employeeProduct.getEmployee().getSurname();
            ProductDTO product = employeeProduct.getProduct();

            if(result.containsKey(fullName)){
                List<ProductDTO> products = result.get(fullName);
                products.add(product);
            }
            else{
                List<ProductDTO> products = new ArrayList<>();
                products.add(product);
                result.put(fullName, products);
            }

        }

        return result;
    }

}
