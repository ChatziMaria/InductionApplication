package gr.knowledge.induction.mapper;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.VacationRequest;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.VacationRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class VacationRequestMapper extends BaseMapper<VacationRequest, VacationRequestDTO>{

    private  final ModelMapper modelMapper;

    @Autowired
    public VacationRequestMapper(ModelMapper modelMapper){
        super(modelMapper, VacationRequest.class, VacationRequestDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(VacationRequest vacationRequest, VacationRequestDTO vacationRequestDTO){
        if(vacationRequest != null && vacationRequestDTO!= null){
            modelMapper.map(vacationRequestDTO , vacationRequest);
        }
    }

}
