package gr.knowledge.induction.mapper;

import gr.knowledge.induction.annotation.MapperBean;
import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.EmployeeProduct;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.EmployeeProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@MapperBean
public class EmployeeProductMapper extends BaseMapper<EmployeeProduct, EmployeeProductDTO>{

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeProductMapper(ModelMapper modelMapper){
        super(modelMapper, EmployeeProduct.class, EmployeeProductDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(EmployeeProduct employeeProduct, EmployeeProductDTO employeeProductDTO){
        if(employeeProduct != null && employeeProductDTO != null){
            modelMapper.map(employeeProductDTO , employeeProduct);
        }
    }
}
