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

/**
 * Υλοποίηση της υπηρεσίας EmployeeProductService.
 * Παρέχει λειτουργίες δημιουργίας, ανάκτησης, ενημέρωσης, διαγραφής και ομαδοποίησης προϊόντων
 * ανά εργαζόμενο για μια εταιρεία.
 */
@Service
public class EmployeeProductServiceImpl implements EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    private final EmployeeProductMapper employeeProductMapper;

    private  final ProductService productService;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param employeeProductRepository το repository για τις σχέσεις εργαζομένων-προϊόντων
     * @param productService            η υπηρεσία προϊόντων
     * @param employeeProductMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public EmployeeProductServiceImpl(EmployeeProductRepository employeeProductRepository, ProductService productService, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.productService = productService;
        this.employeeProductMapper = employeeProductMapper;
    }

    /**
     * Δημιουργεί μια νέα σχέση εργαζομένου-προϊόντος.
     *
     * @param employeeProduct το αντικείμενο DTO που περιγράφει τη σχέση
     * @return το δημιουργημένο EmployeeProductDTO
     */
    @Override
    public EmployeeProductDTO createEmployeeProduct(EmployeeProductDTO employeeProduct){
        return  employeeProductMapper.toDTO(employeeProductRepository.save(employeeProductMapper.toEntity(employeeProduct)));
    }

    /**
     * Επιστρέφει όλες τις σχέσεις εργαζομένων με προϊόντα.
     *
     * @return λίστα αντικειμένων EmployeeProductDTO
     */
    @Override
    public List<EmployeeProductDTO> getAllEmployeeProducts() {
        return employeeProductMapper.toDTO(employeeProductRepository.findAll());
    }

    /**
     * Επιστρέφει μια σχέση εργαζομένου-προϊόντος με βάση το ID.
     *
     * @param id το ID της σχέσης
     * @return το αντικείμενο EmployeeProductDTO
     * @throws EntityNotFoundException αν δεν βρεθεί η σχέση με το συγκεκριμένο ID
     */
    @Override
    public EmployeeProductDTO getEmployeeProductById(Long id) {

        return employeeProductMapper.toDTO(employeeProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));
    }

    /**
     * Ενημερώνει μια υπάρχουσα σχέση εργαζομένου-προϊόντος με βάση το ID.
     *
     * @param id              το ID της σχέσης που θα ενημερωθεί
     * @param employeeProduct το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return το ενημερωμένο EmployeeProductDTO
     */
    @Override
    public EmployeeProductDTO updateEmployeeProduct(Long id, EmployeeProductDTO employeeProduct){

        EmployeeProductDTO currentEmployeeProduct = getEmployeeProductById(id);

        employeeProductMapper.updateEntityFromDTO(employeeProductMapper.toEntity(currentEmployeeProduct), employeeProduct );

        return employeeProductMapper.toDTO(employeeProductRepository.save(employeeProductMapper.toEntity(employeeProduct)));
    }

    /**
     * Διαγράφει μια σχέση εργαζομένου-προϊόντος με βάση το ID.
     *
     * @param id το ID της σχέσης που θα διαγραφεί
     * @throws EntityNotFoundException αν δεν βρεθεί η σχέση με το συγκεκριμένο ID
     */
    @Override
    public void deleteEmployeeProduct(Long id){
        EmployeeProductDTO employeeProduct = employeeProductMapper.toDTO(employeeProductRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));
        employeeProductRepository.deleteById(id);

    }

    /**
     * Επιστρέφει όλα τα προϊόντα μιας εταιρείας, ομαδοποιημένα ανά εργαζόμενο.
     *
     * @param companyId το ID της εταιρείας
     * @return χάρτης (Map) όπου το κλειδί είναι το πλήρες όνομα του εργαζομένου και η τιμή η λίστα προϊόντων του
     */
    @Override
    public Map<String,List<ProductDTO>> getAllCompanyProducts(Long companyId){

       List<EmployeeProductDTO> employeeProducts = employeeProductMapper.toDTO(employeeProductRepository.findEmployeesAndProductsByCompanyId(companyId));

       Map<String,List<ProductDTO>> result = groupProductsByEmployee(employeeProducts);

        return result;
    }

    /**
     * Βοηθητική μέθοδος που ομαδοποιεί προϊόντα ανά εργαζόμενο.
     *
     * @param employeeProducts λίστα σχέσεων εργαζομένων-προϊόντων
     * @return χάρτης (Map) όπου το κλειδί είναι το πλήρες όνομα του εργαζομένου και η τιμή η λίστα προϊόντων του
     */
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
