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
@Builder(builderMethodName = "employeeDtoBuilder")

public class EmployeeDTO  {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate startDate;
    private Integer vacationDays;
    private BigDecimal salary;
    private String employmentType;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }
    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    public BigDecimal getSalary(){
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public CompanyDTO getCompany() {
        return company;
    }
    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

}
