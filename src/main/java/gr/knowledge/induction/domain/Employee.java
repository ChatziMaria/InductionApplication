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
@Table(name = "employee")
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
    private BigDecimal salary;

    @Column(name = "employment_type", nullable = false, length = 20)
    private String employmentType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


}
