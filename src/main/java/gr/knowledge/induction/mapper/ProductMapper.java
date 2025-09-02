package gr.knowledge.induction.mapper;

import gr.knowledge.induction.annotation.MapperBean;
import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Product;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@MapperBean
public class ProductMapper extends BaseMapper<Product, ProductDTO>{

    private final ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper){
        super(modelMapper, Product.class, ProductDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(Product product, ProductDTO productDTO){
        if(product != null && productDTO != null){
            modelMapper.map(productDTO , product);
        }
    }
}
