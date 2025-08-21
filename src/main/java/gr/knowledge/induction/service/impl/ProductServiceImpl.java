package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.repository.ProductRepository;
import gr.knowledge.induction.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product, Long id){
        return  productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id));
    }

    @Override
    public Product updateProduct(Long id , Product product){

        Product currentProduct = getProductById(id);

        Product result = new Product();

        result.setId(currentProduct.getId());
        result.setName(product.getName());
        result.setDescription(product.getDescription());
        result.setBarcode(product.getBarcode());

        return productRepository.save(result);
    }

    @Override
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                });;

        productRepository.deleteById(id);


    }

}
