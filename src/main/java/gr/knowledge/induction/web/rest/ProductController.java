package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.ProductDTO;
import gr.knowledge.induction.service.EmployeeService;
import gr.knowledge.induction.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller για τη διαχείρηση προιόντων.
 * Παρέχει endpoints για δημιουργία ,ενημέρωση , διαγραφή, ανάκτηση προιόντων.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Κατασκευαστής για την έγχυση του ProductService.
     *
     * @param productService η υπηρεσία που διαχειρίζεται τα προιόντα
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Δημιουργεί ένα νέο προϊόν.
     *
     * @param product το αντικείμενο DTO του προϊόντος
     * @return ResponseEntity με το δημιουργημένο αντικείμενο ProductDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product){
        ProductDTO createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Ενημερώνει ένα υπάρχων προϊόν με βάση το ID.
     *
     * @param id              το ID του προϊόντος που θα ενημερωθεί
     * @param product αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο ProductDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@RequestBody ProductDTO product){

        ProductDTO updatedProduct = productService.updateProduct(id,product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Διαγράφει ένα προϊόν με βάση το ID.
     *
     * @param id το ID του προϊόντος που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();

    }

    /**
     * Επιστρέφει όλα τα προϊόντα.
     *
     * @return ResponseEntity με λίστα αντικειμένων ProductDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> gotAllProducts = productService.getAllProducts();
        return ResponseEntity.ok( gotAllProducts);
    }

    /**
     * Επιστρέφει ένα προϊόν με βάση το ID.
     *
     * @param id το ID του προϊόντος
     * @return ResponseEntity με το αντικείμενο ProductDTO
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO product = productService.getProductById(id);
        return  ResponseEntity.ok(product);
    }


}

