package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.repository.ProductRepository;
import gr.knowledge.induction.service.ProductService;
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
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Long id , Product product){
        Product result = new Product();
        Optional<Product> currentProduct = getProductById(id);

        if(currentProduct.isPresent()){
            result.setId(currentProduct.get().getId());
            result.setName(product.getName());
            result.setDescription(product.getDescription());
            result.setBarcode(product.getBarcode());
        }
        else{
            throw new RuntimeException();
        }
        return productRepository.save(result);
    }

    @Override
    public void deleteProduct(Long id){
        Optional<Product> product = getProductById(id);

        if(product.isPresent()){
            productRepository.deleteById(id);
        }
        else{ throw new RuntimeException();
        }

    }

}
