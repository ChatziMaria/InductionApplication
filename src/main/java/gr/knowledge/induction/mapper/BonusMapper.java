package gr.knowledge.induction.mapper;

import gr.knowledge.induction.annotation.MapperBean;
import gr.knowledge.induction.domain.Bonus;

import gr.knowledge.induction.dto.BonusDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@MapperBean
public class BonusMapper extends BaseMapper<Bonus, BonusDTO>{

    private  final ModelMapper modelMapper;

    @Autowired
    public BonusMapper(ModelMapper modelMapper){
        super(modelMapper, Bonus.class, BonusDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(Bonus bonus, BonusDTO bonusDTO){
        if(bonus != null && bonusDTO != null){
            modelMapper.map(bonusDTO , bonus);
        }
    }

}
