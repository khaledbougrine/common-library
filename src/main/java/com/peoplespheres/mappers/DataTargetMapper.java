package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.domain.DataTargetDomain;
import com.peoplespheres.dto.DataTargetDTO;
import com.peoplespheres.dto.DataTargetFullDTO;
import com.peoplespheres.entites.DataTargetEntity;

// Lombok imports
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

/** Mapper from data target entity to data target domain instance */
public final class DataTargetMapper {
    /** Private default constructor to prevent instantiation */
    private DataTargetMapper() {}

    /** Mapper transform data target entity into data target domain instance.
     *
     * @param dataTargetEntity The data target entity to map into a data target domain instance.
     * @return DataTargetDomain - The data target domain instance mapped from the data target entity.
     */
    public static @NonNull DataTargetDomain fromEntityToDomain(@NonNull final DataTargetEntity dataTargetEntity) {
        final DataTargetDomain dataTargetDomain = new DataTargetDomain();
        BeanUtils.copyProperties(dataTargetEntity, dataTargetDomain);

        // Converting PSO type if necessary
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isPsoTypeLoaded = persistentUtil.isLoaded(dataTargetEntity, "psoType");
        if (isPsoTypeLoaded && (dataTargetEntity.getPsoType() != null)) {
            dataTargetDomain.setPsoTypeAlias(dataTargetEntity.getPsoType().getAlias());
            dataTargetDomain.setPsoTypeId(dataTargetEntity.getPsoType().getId());
        }

        // Converting related PSO type if necessary
        final boolean isRelatedPsoTypeLoaded = persistentUtil.isLoaded(dataTargetEntity, "related_pso_type");
        if (isRelatedPsoTypeLoaded && (dataTargetEntity.getRelatedPsoType() != null)) {
            dataTargetDomain.setRelatedPso(dataTargetEntity.getRelatedPsoType().getAlias());
            dataTargetDomain.setRelatedPsoTypeId(dataTargetEntity.getRelatedPsoType().getId());
        }

        // Returning the converted
        return dataTargetDomain;
    }

    /** Mapper transform data target domain instance into data target entity.
     *
     * @param dataTargetDomain The data target domain instance to map into a data target entity.
     * @return DataTargetEntity - The data target entity mapped from the data target domain instance.
     */
    public static @NonNull DataTargetEntity fromDomainToEntity(@NonNull final DataTargetDomain dataTargetDomain) {
        final DataTargetEntity dataTargetEntity = new DataTargetEntity();
        BeanUtils.copyProperties(dataTargetDomain, dataTargetEntity);
        return dataTargetEntity;
    }

    /** Mapper transform data target entity into data target domain instance.
     *
     * @param dataTargetDTO The data target DTO to map into a data target domain instance.
     * @return DataTargetDomain - The data target domain instance mapped from the data target DTO.
     */
    public static @NonNull DataTargetDomain fromDtoToDomain(@NonNull final DataTargetDTO dataTargetDTO) {
        final DataTargetDomain dataTargetDomain = new DataTargetDomain();
        BeanUtils.copyProperties(dataTargetDTO, dataTargetDomain);
        return dataTargetDomain;
    }

    /** Mapper transform data target domain instance into data target DTO.
     *
     * @param dataTargetDomain The data target domain instance to map into a data target DTO.
     * @return DataTargetDTO - The data target DTO mapped from the data target domain instance.
     */
    public static @NonNull DataTargetDTO fromDomainToDTO(@NonNull final DataTargetDomain dataTargetDomain) {
        final DataTargetDTO dataTargetDTO = new DataTargetDTO();
        BeanUtils.copyProperties(dataTargetDomain, dataTargetDTO);
        dataTargetDTO.setTechnicalId(dataTargetDomain.getTechnicalId());
        dataTargetDTO.setFieldType(dataTargetDomain.getFieldType());
        dataTargetDTO.setAlias(dataTargetDomain.getAlias());
        dataTargetDTO.setDefaultValue(dataTargetDomain.getDefaultValue());
        dataTargetDTO.setIsRequired(dataTargetDomain.getIsRequired());
        dataTargetDTO.setName(dataTargetDomain.getName());
        dataTargetDTO.setIsArray(dataTargetDomain.getIsArray());
        return dataTargetDTO;
    }

    /** Mapper transform data target domain instance into data target DTO.
     *
     * @param dataTargetDomain The data target domain instance to map into a data target DTO.
     * @return DataTargetDTO - The data target DTO mapped from the data target domain instance.
     */
    public static @NonNull DataTargetFullDTO fromDomainToFullDTO(@NonNull final DataTargetDomain dataTargetDomain) {
        final DataTargetFullDTO dataTargetFullDTO = new DataTargetFullDTO();
        BeanUtils.copyProperties(dataTargetDomain, dataTargetFullDTO);
        dataTargetFullDTO.setTechnicalId(dataTargetDomain.getTechnicalId());
        dataTargetFullDTO.setAlias(dataTargetDomain.getAlias());
        dataTargetFullDTO.setCategoryAlias(dataTargetDomain.getCategoryAlias());
        dataTargetFullDTO.setDefaultValue(dataTargetDomain.getDefaultValue());
        dataTargetFullDTO.setIsRequired(dataTargetDomain.getIsRequired());
        dataTargetFullDTO.setName(dataTargetDomain.getName());
        dataTargetFullDTO.setParentAlias(dataTargetDomain.getParentAlias());
        dataTargetFullDTO.setParentFieldName(dataTargetDomain.getParentFieldName());
        dataTargetFullDTO.setIsArray(dataTargetDomain.getIsArray());
        return dataTargetFullDTO;
    }

    /** Mapper transform data target entity into data target domain instance.
     *
     * @param dataTargetFullDTO The data target DTO to map into a data target domain instance.
     * @return DataTargetDomain - The data target domain instance mapped from the data target DTO.
     */
    public static @NonNull DataTargetDomain fromFullDtoToDomain(@NonNull final DataTargetFullDTO dataTargetFullDTO) {
        final DataTargetDomain dataTargetDomain = new DataTargetDomain();
        BeanUtils.copyProperties(dataTargetFullDTO, dataTargetDomain);
        dataTargetDomain.setTechnicalId(dataTargetFullDTO.getTechnicalId());
        dataTargetDomain.setFieldType(dataTargetFullDTO.getFieldType());
        dataTargetDomain.setAlias(dataTargetFullDTO.getAlias());
        dataTargetDomain.setCategoryAlias(dataTargetFullDTO.getCategoryAlias());
        dataTargetDomain.setDefaultValue(dataTargetFullDTO.getDefaultValue());
        dataTargetDomain.setIsRequired(dataTargetFullDTO.getIsRequired());
        dataTargetDomain.setName(dataTargetFullDTO.getName());
        dataTargetDomain.setParentAlias(dataTargetFullDTO.getParentAlias());
        dataTargetDomain.setParentFieldName(dataTargetFullDTO.getParentFieldName());
        dataTargetDomain.setIsArray(dataTargetFullDTO.getIsArray());
        return dataTargetDomain;
    }
}
