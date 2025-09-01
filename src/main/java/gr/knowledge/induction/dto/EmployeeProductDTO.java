package gr.knowledge.induction.dto;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.EmployeeProduct;
import lombok.*;
import org.springframework.beans.BeanMetadataAttribute;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(builderMethodName = "employeeProductDtoBuilder")

public class EmployeeProductDTO extends EmployeeProduct {

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



