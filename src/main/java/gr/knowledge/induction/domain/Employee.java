package gr.knowledge.induction.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "seq_employee" , sequenceName = "seq_employee" , allocationSize = 50)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "surname", nullable = false, length = 255)
    private String surname;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "vacation_days", nullable = false)
    private Integer vacationDays;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "employment_type", nullable = false, length = 20)
    private String employmentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    //getters and setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
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
    public void setEmail(String email){
        this.email = email;
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public Integer getVacationDays(){
        return vacationDays;
    }
    public void setVacationDays(Integer vacationDays){
        this.vacationDays = vacationDays;
    }

    public Double getSalary(){
        return salary;
    }
    public void setSalary(Double salary){
        this.salary = salary;
    }

    public String getEmploymentType(){
        return employmentType;
    }
    public void setEmploymentType(String employmentType){
        this.employmentType = employmentType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
