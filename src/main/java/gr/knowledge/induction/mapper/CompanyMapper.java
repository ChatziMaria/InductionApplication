package gr.knowledge.induction.mapper;

import gr.knowledge.induction.annotation.MapperBean;
import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Company;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.CompanyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@MapperBean
public class CompanyMapper extends BaseMapper<Company, CompanyDTO>{

    private final  ModelMapper modelMapper;

    @Autowired
    public CompanyMapper(ModelMapper modelMapper){
        super(modelMapper, Company.class, CompanyDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(Company company, CompanyDTO companyDTO){
        if(company != null && companyDTO != null){
            modelMapper.map(companyDTO , company);
        }
    }

}
