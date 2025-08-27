package gr.knowledge.induction.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "seq_employee_product", sequenceName = "seq_employee_product", allocationSize = 50)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //getters and setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public  Product getProduct(){
        return product;
    }
    public void setProduct(Product product){
        this.product = product;
    }

    public static class EmployeeProductDTO {
        private final String fullName;
        private final Product product;

        public EmployeeProductDTO(Employee employee, Product product) {
            this.fullName = employee.getName() + " " + employee.getSurname();
            this.product = product;
        }

        public String getFullName(){
            return  fullName;
        }

        public Product getProduct(){
            return  product;
        }

    }


}
