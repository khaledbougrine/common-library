package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.DataSourceDTO;
import com.peoplespheres.dto.DataSourceFullDTO;
import com.peoplespheres.entites.DataSourceEntity;
import com.peoplespheres.domain.DataSourceDomain;

// Lombok imports
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

// Javax imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

/** Mapper from data source entity to data source domain instance */
public  class DataSourceMapper {
    /** Private default constructor to prevent instantiation */
    private DataSourceMapper() {}

    /** Mapper transform data source entity into data source domain instance.
     *
     * @param dataSourceEntity The data source entity to map into a data source domain instance.
     * @return DataSourceDomain The data source domain instance mapped from the data source entity.
     */
    public static @NonNull DataSourceDomain fromEntityToDomain(@NonNull final DataSourceEntity dataSourceEntity) {
        final DataSourceDomain dataSourceDomain = new DataSourceDomain();
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDomain);

        // Converting PSO type if necessary
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isPsoTypeLoaded = persistentUtil.isLoaded(dataSourceEntity, "psoType");
        if (isPsoTypeLoaded && (dataSourceEntity.getPsoType() != null)) {
            dataSourceDomain.setPsoTypeAlias(dataSourceEntity.getPsoType().getAlias());
            dataSourceDomain.setPsoTypeId(dataSourceEntity.getPsoType().getId());
        }

        // Converting related PSO type if necessary
        final boolean isRelatedPsoTypeLoaded = persistentUtil.isLoaded(dataSourceEntity, "related_pso_type");
        if (isRelatedPsoTypeLoaded && (dataSourceEntity.getRelatedPsoType() != null)) {
            dataSourceDomain.setRelatedPso(dataSourceEntity.getRelatedPsoType().getAlias());
            dataSourceDomain.setRelatedPsoTypeId(dataSourceEntity.getRelatedPsoType().getId());
        }

        // Returning the converted
        return dataSourceDomain;
    }

    /** Mapper transform data source domain instance into data source entity.
     *
     * @param dataSourceDomain The data source entity to map into a data source domain instance.
     * @return DataSourceDomain The data source domain instance mapped from the data source entity.
     */
    public static @NonNull DataSourceEntity fromDomainToEntity(@NonNull final DataSourceDomain dataSourceDomain) {
        final DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(dataSourceDomain, dataSourceEntity);
        return dataSourceEntity;
    }

    /** Mapper transform data source DTO into data source domain instance.
     *
     * @param dataSourceDTO The mapping DTO to map into a mapping domain instance.
     * @return DataSourceDomain The data source domain instance mapped from the data source DTO.
     */
    public static @NonNull DataSourceDomain fromDtoToDomain(@NonNull final DataSourceDTO dataSourceDTO) {
        final DataSourceDomain dataSourceDomain = new DataSourceDomain();
        BeanUtils.copyProperties(dataSourceDTO, dataSourceDomain);
        return dataSourceDomain;
    }

    /** Mapper transform data source domain instance into data source DTO.
     *
     * @param dataSourceDomain The data source DTO to map into a data source domain instance.
     * @return DataSourceDTO - The data source DTO mapped from the data source domain instance.
     */
    public static @NonNull DataSourceDTO fromDomainToDTO(@NonNull final DataSourceDomain dataSourceDomain) {
        final DataSourceDTO dataSourceDTO = new DataSourceDTO();
        BeanUtils.copyProperties(dataSourceDomain, dataSourceDTO);
        dataSourceDTO.setFieldType(dataSourceDomain.getFieldType());
        dataSourceDTO.setAlias(dataSourceDomain.getAlias());
        dataSourceDTO.setCategoryAlias(dataSourceDomain.getCategoryAlias());
        dataSourceDTO.setDefaultValue(dataSourceDomain.getDefaultValue());
        dataSourceDTO.setIsRequired(dataSourceDomain.getIsRequired());
        dataSourceDTO.setName(dataSourceDomain.getName());
        dataSourceDTO.setParentAlias(dataSourceDomain.getParentAlias());
        dataSourceDTO.setParentFieldName(dataSourceDomain.getParentFieldName());
        dataSourceDTO.setIsArray(dataSourceDomain.getIsArray());
        dataSourceDTO.setPsoTypeId(dataSourceDomain.getPsoTypeId());
        dataSourceDTO.setPsoTypeAlias(dataSourceDomain.getPsoTypeAlias());
        dataSourceDTO.setRelatedPsoTypeId(dataSourceDomain.getRelatedPsoTypeId());
        dataSourceDTO.setRelatedPsoTypeAlias(dataSourceDomain.getRelatedPso());
        return dataSourceDTO;
    }

    /** Mapper transform data source domain instance into data source DTO.
     *
     * @param dataSourceDomain The data source DTO to map into a data source domain instance.
     * @return DataSourceDTO - The data source DTO mapped from the data source domain instance.
     */
    public static @NonNull DataSourceFullDTO fromDomainToFullDTO(@NonNull final DataSourceDomain dataSourceDomain) {
        final DataSourceFullDTO dataSourceFullDTO = new DataSourceFullDTO();
        BeanUtils.copyProperties(dataSourceDomain, dataSourceFullDTO);
        return dataSourceFullDTO;
    }

    /** Mapper transform data source DTO into data source domain instance.
     *
     * @param dataSourceFullDTO The mapping DTO to map into a mapping domain instance.
     * @return DataSourceDomain The data source domain instance mapped from the data source DTO.
     */
    public static @NonNull DataSourceDomain fromFullDtoToDomain(@NonNull final DataSourceFullDTO dataSourceFullDTO) {
        final DataSourceDomain dataSourceDomain = new DataSourceDomain();
        BeanUtils.copyProperties(dataSourceFullDTO, dataSourceDomain);
        if (dataSourceFullDTO.getFieldType() != null) dataSourceDomain.setFieldType(dataSourceFullDTO.getFieldType());
        dataSourceDomain.setAlias(dataSourceFullDTO.getAlias());
        dataSourceDomain.setCategoryAlias(dataSourceFullDTO.getCategoryAlias());
        dataSourceDomain.setDefaultValue(dataSourceFullDTO.getDefaultValue());
        dataSourceDomain.setIsRequired(dataSourceFullDTO.getIsRequired());
        dataSourceDomain.setName(dataSourceFullDTO.getName());
        dataSourceDomain.setParentAlias(dataSourceFullDTO.getParentAlias());
        dataSourceDomain.setParentFieldName(dataSourceFullDTO.getParentFieldName());
        dataSourceDomain.setIsArray(dataSourceFullDTO.getIsArray());
        return dataSourceDomain;
    }
}
