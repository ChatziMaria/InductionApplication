package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
