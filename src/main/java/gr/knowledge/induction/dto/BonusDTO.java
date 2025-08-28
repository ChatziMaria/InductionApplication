package gr.knowledge.induction.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BonusDTO {

    private Long id;
    private BigDecimal amount;
    private EmployeeDTO employee;
    private CompanyDTO company;

}
