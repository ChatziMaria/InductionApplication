package gr.knowledge.induction.dto;

import gr.knowledge.induction.domain.Employee;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class EmployeeProductDTO {

    private Long id;
    private EmployeeDTO employeeDTO;
    private ProductDTO productDTO;




}



