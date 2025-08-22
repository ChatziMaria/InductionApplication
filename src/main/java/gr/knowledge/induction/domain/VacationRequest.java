package gr.knowledge.induction.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VacationRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "seq_vacation_request", sequenceName =  "seq_vacation_request", allocationSize = 50)
    private Long id;

    @Column(name = "start_date", nullable = false )
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "days", nullable = false)
    private Integer days;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VacationStatus status;

    //getters and setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }
    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public VacationStatus getStatus(){
        return status;
    }
    public void setStatus(VacationStatus status) {
        this.status = status;
    }

    public Integer getDays(){
        return days;
    }
    public void setDays(Integer days){
        this.days = days;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}


