package gr.knowledge.induction.mapper;

import gr.knowledge.induction.annotation.MapperBean;
import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@MapperBean
public class EmployeeMapper extends BaseMapper<Employee, EmployeeDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper){
        super(modelMapper, Employee.class, EmployeeDTO.class);
        this.modelMapper = modelMapper;

    }

    public  void updateEntityFromDTO(Employee employee, EmployeeDTO employeeDTO){
        if(employee != null && employeeDTO != null){
            modelMapper.map(employeeDTO , employee);
        }
    }
}
