package gr.knowledge.induction.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "seq_bonus" , sequenceName = "seq_bonus" , allocationSize = 50)
    private Long id;

    @Column(name = "amount", nullable = false )
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

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

    public BigDecimal getAmount(){
        return amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public Company getCompany(){
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
