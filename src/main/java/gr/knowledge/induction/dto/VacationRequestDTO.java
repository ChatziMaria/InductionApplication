package gr.knowledge.induction.dto;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.domain.VacationStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class VacationRequestDTO extends VacationRequest {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer days;
    private EmployeeDTO employee;
    private VacationStatus status;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getDays(){
        return days;
    }
    public void setDays(Integer days) {
        this.days = days;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }
    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public VacationStatus getStatus() {
        return status;
    }
    public void setStatus(VacationStatus status) {
        this.status = status;
    }

}
