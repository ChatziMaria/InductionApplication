package gr.knowledge.induction.dto;

import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class EmployeeDTO extends Employee {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate startDate;
    private Integer vacationDays;
    private BigDecimal salary;
    private String employmentType;
    private CompanyDTO company;
}
