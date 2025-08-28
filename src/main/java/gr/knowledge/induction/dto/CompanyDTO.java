package gr.knowledge.induction.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CompanyDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;

}
