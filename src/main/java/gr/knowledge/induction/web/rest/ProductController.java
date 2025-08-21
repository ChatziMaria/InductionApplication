package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.service.EmployeeService;
import gr.knowledge.induction.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product){

        Product updatedProduct = productService.updateProduct(id,product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> gotAllProducts = productService.getAllProducts();
        return ResponseEntity.ok( gotAllProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return  ResponseEntity.ok(product);
    }


}

