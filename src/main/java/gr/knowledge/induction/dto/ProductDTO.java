package gr.knowledge.induction.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String barcode;

}
