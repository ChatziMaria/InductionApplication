package gr.knowledge.induction.mapper;


import org.modelmapper.ModelMapper;

import java.util.List;

public class BaseMapper<E,D>{

    private final ModelMapper modelMapper;
    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    public BaseMapper(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    /**
     * Converts an entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    public D toDTO(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Converts a list of entities to a list of DTOs.
     *
     * @param entityList the list of entities
     * @return the list of DTOs
     */
    public List<D> toDTO(List<E> entityList) {
        return entityList.stream()
                .map(this::toDTO)
                .toList();
    }


    /**
     * Converts a DTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the entity
     */
    public E toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    /**
     * Converts a list of DTOs to a list of entities.
     *
     * @param dtoList the list of DTOs
     * @return the list of entities
     */
    public List<E> toEntity(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .toList();
    }

}
