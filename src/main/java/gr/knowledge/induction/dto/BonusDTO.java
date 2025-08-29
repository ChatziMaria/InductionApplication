package gr.knowledge.induction.dto;


import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.domain.Employee;
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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Setter
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public EmployeeDTO getEmployee(){
        return employee;
    }

    public void setEmployee(EmployeeDTO employee){
        this.employee = employee;
    }

    public CompanyDTO getCompany(){
        return company;
    }

    public void setCompany(CompanyDTO company){
        this.company = company;
    }

}
