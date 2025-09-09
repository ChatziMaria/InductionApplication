package gr.knowledge.induction.service.impl;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.EmployeeDTO;
import gr.knowledge.induction.mapper.EmployeeMapper;
import gr.knowledge.induction.repository.CompanyRepository;
import gr.knowledge.induction.repository.EmployeeRepository;
import gr.knowledge.induction.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Υλοποίηση της υπηρεσίας EmployeeService.
 * Παρέχει λειτουργίες δημιουργίας, ανάκτησης, ενημέρωσης, διαγραφής εργαζομένων,
 * υπολογισμού μηνιαίων εξόδων και επιστροφής εργαζομένων μιας εταιρείας.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    /**
     * Κατασκευαστής για την έγχυση των εξαρτήσεων της υπηρεσίας.
     *
     * @param employeeRepository το repository για τους εργαζομένους
     * @param employeeMapper     ο mapper για μετατροπή μεταξύ entity και DTO
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;

    }

    /**
     * Δημιουργεί έναν νέο εργαζόμενο.
     *
     * @param employee το αντικείμενο DTO που περιγράφει τον εργαζόμενο
     * @return το δημιουργημένο EmployeeDTO
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee){
        return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employee)));
    }

    /**
     * Επιστρέφει όλους τους εργαζομένους.
     *
     * @return λίστα αντικειμένων EmployeeDTO
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.toDTO(employeeRepository.findAll());
    }

    /**
     * Επιστρέφει έναν εργαζόμενο με βάση το ID.
     *
     * @param id το ID του εργαζομένου
     * @return το αντικείμενο EmployeeDTO
     * @throws EntityNotFoundException αν δεν βρεθεί εργαζόμενος με το συγκεκριμένο ID
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException()));
    }

    /**
     * Ενημερώνει έναν υπάρχοντα εργαζόμενο με βάση το ID.
     *
     * @param id       το ID του εργαζομένου που θα ενημερωθεί
     * @param employee το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return το ενημερωμένο EmployeeDTO
     */
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee){

        EmployeeDTO currentEmployee = getEmployeeById(id);
        employeeMapper.updateEntityFromDTO(employeeMapper.toEntity(currentEmployee), employee);

        return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(currentEmployee)));
    }

    /**
     * Διαγράφει έναν εργαζόμενο με βάση το ID.
     *
     * @param id το ID του εργαζομένου που θα διαγραφεί
     * @throws EntityNotFoundException αν δεν βρεθεί εργαζόμενος με το συγκεκριμένο ID
     */
    @Override
    public void deleteEmployee(Long id){
        EmployeeDTO employee = employeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() -> {
                    return new EntityNotFoundException();
                }));

        employeeRepository.deleteById(id);

    }

    /**
     * Υπολογίζει τα συνολικά μηνιαία έξοδα μισθοδοσίας για μια εταιρεία.
     *
     * @param companyId το ID της εταιρείας
     * @return το συνολικό ποσό μισθών ως BigDecimal
     */
    @Override
    public BigDecimal calculateMonthlyExpenses(Long companyId){

        List<EmployeeDTO> companyEmployees = employeeMapper.toDTO(employeeRepository.findByCompanyId(companyId));

       BigDecimal totalSalary = BigDecimal.valueOf(0);
       for (EmployeeDTO employee : companyEmployees) {
           totalSalary.add(employee.getSalary());
       }
       return totalSalary;
    }


    /**
     * Επιστρέφει όλους τους εργαζομένους μιας εταιρείας.
     *
     * @param companyId το ID της εταιρείας
     * @return λίστα αντικειμένων EmployeeDTO
     */
    @Override
    public List<EmployeeDTO> returnEmployees(Long companyId){
        return employeeMapper.toDTO(employeeRepository.findByCompanyId(companyId));
    }

    /**
     * Αποθηκεύει έναν εργαζόμενο (create/update).
     *
     * @param employee το αντικείμενο EmployeeDTO
     * @return το αποθηκευμένο EmployeeDTO
     */
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employee){
        return  employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employee)));
    }
}
