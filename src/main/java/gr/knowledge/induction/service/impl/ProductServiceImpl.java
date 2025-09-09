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

/**
 * Υλοποίηση της υπηρεσίας ProductService.
 * Παρέχει λειτουργίες δημιουργίας, ανάκτησης, ενημέρωσης και διαγραφής προϊόντων.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param productRepository το repository για τα προϊόντα
     * @param productMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }

    /**
     * Δημιουργεί ένα νέο προϊόν.
     *
     * @param product το αντικείμενο DTO που περιγράφει το προϊόν
     * @return το δημιουργημένο ProductDTO
     */
    @Override
    public ProductDTO createProduct(ProductDTO product){
        return  productMapper.toDTO(productRepository.save(productMapper.toEntity(product)));
    }

    /**
     * Επιστρέφει όλα τα προϊόντα.
     *
     * @return λίστα αντικειμένων ProductDTO
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTO(productRepository.findAll());
    }

    /**
     * Επιστρέφει ένα προϊόν με βάση το ID.
     *
     * @param id το ID του προϊόντος
     * @return το αντικείμενο ProductDTO
     * @throws EntityNotFoundException αν δεν βρεθεί προϊόν με το συγκεκριμένο ID
     */
    @Override
    public ProductDTO getProductById(Long id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bonus not found with id " + id)));
    }

    /**
     * Ενημερώνει ένα υπάρχον προϊόν με βάση το ID.
     *
     * @param id      το ID του προϊόντος που θα ενημερωθεί
     * @param product το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return το ενημερωμένο ProductDTO
     */
    @Override
    public ProductDTO updateProduct(Long id , ProductDTO product){

        ProductDTO currentProduct = getProductById(id);
        productMapper.updateEntityFromDTO(productMapper.toEntity(currentProduct),product);

        return productMapper.toDTO(productRepository.save(productMapper.toEntity(currentProduct)));
    }

    /**
     * Διαγράφει ένα προϊόν με βάση το ID.
     *
     * @param id το ID του προϊόντος που θα διαγραφεί
     * @throws EntityNotFoundException αν δεν βρεθεί προϊόν με το συγκεκριμένο ID
     */
    @Override
    public void deleteProduct(Long id){
        ProductDTO product = productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        productRepository.deleteById(id);


    }

}
