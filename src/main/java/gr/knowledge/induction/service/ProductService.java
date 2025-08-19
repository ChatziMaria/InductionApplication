package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product, Long id);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
