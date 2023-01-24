package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.FieldDTO;
import com.peoplespheres.entites.FieldEntity;
import com.peoplespheres.domain.*;


// Lombok imports
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

public final class FieldMapper {
    /** Private default constructor to prevent instantiation */
    private FieldMapper() {}

    /** Mapping field entity to field domain instance.
     *
     * @param fieldEntity The field entity to map to a field domain instance.
     * @return FieldDomain - The field domain instance mapped from the field entity.
     */
    public static @NonNull FieldDomain fromEntityToDomain(@NonNull final FieldEntity fieldEntity) {
        final FieldDomain fieldDomain = new FieldDomain();
        BeanUtils.copyProperties(fieldEntity, fieldDomain);

        // Converting if necessary the associated field category
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isCategoryLoaded = persistentUtil.isLoaded(fieldEntity, "category");
        if (isCategoryLoaded && (null != fieldEntity.getCategory())) {
            final FieldCategoryDomain fieldCategoryDomain = new FieldCategoryDomain();
            BeanUtils.copyProperties(fieldEntity.getCategory(), fieldCategoryDomain);
            fieldDomain.setCategory(fieldCategoryDomain);
        }

        // Converting if necessary the associated PSO type
        final boolean isPsoTypeLoaded = persistentUtil.isLoaded(fieldEntity, "psoType");
        if (isPsoTypeLoaded) {
            fieldDomain.setPsoTypeAlias(fieldEntity.getPsoType().getAlias());
            fieldDomain.setPsoTypeName(fieldEntity.getPsoType().getName());
            fieldDomain.setPsoTypeId(fieldEntity.getPsoType().getId());
        }

        // Returning the converted field
        return fieldDomain;
    }

    /** Mapper to convert a field domain instance to a field DTO.
     *
     * @param fieldDomain The field domain instance to map/convert to a field DTO.
     * @return FieldDTO The field DTO result from the conversion of the domain instance.
     */
    public static @NonNull FieldDTO fromDomainToDTO(@NonNull final FieldDomain fieldDomain) {
        // Building the associated field DTO
        final FieldDTO fieldDTO = new FieldDTO();

        // Mapping the properties from the domain instance to the DTO
        BeanUtils.copyProperties(fieldDomain, fieldDTO);
        if (fieldDomain.getCategory() != null) {
            fieldDTO.setCategoryAlias(fieldDomain.getCategory().getAlias());
            fieldDTO.setCategoryName(fieldDomain.getCategory().getName());
        }

        // Returning the mapped DTO
        return fieldDTO;
    }
}
