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

}
