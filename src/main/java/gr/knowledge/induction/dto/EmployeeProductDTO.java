package gr.knowledge.induction.dto;

import gr.knowledge.induction.domain.Employee;
import lombok.*;
import org.springframework.beans.BeanMetadataAttribute;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class EmployeeProductDTO {

    private Long id;
    private EmployeeDTO employee;
    private ProductDTO product;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct(){
        return product;
    }
    public void setProduct(ProductDTO product){
        this.product = product;
    }

    public EmployeeDTO getEmployee(){
        return employee;
    }
    public void setEmployee(EmployeeDTO employee){
        this.employee = employee;
    }
}



