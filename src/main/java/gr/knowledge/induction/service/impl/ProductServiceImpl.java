package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.ProductDTO;
import gr.knowledge.induction.mapper.ProductMapper;
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

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }

    @Override
    public ProductDTO createProduct(ProductDTO product){
        return  productMapper.toDTO(productRepository.save(productMapper.toEntity(product)));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTO(productRepository.findAll());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

    @Override
    public ProductDTO updateProduct(Long id , ProductDTO product){

        ProductDTO currentProduct = getProductById(id);
        productMapper.updateEntityFromDTO(productMapper.toEntity(currentProduct),product);

        return productMapper.toDTO(productRepository.save(productMapper.toEntity(currentProduct)));
    }

    @Override
    public void deleteProduct(Long id){
        ProductDTO product = productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        productRepository.deleteById(id);


    }

}
