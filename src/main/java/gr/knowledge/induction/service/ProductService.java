package gr.knowledge.induction.service;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.ProductDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    ProductDTO createProduct(ProductDTO product);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, ProductDTO product);
    void deleteProduct(Long id);
}
