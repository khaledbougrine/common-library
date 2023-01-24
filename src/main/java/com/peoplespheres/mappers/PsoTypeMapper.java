package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.FieldCategoryDTO;
import com.peoplespheres.dto.PsoTypeDTO;
import com.peoplespheres.domain.FieldCategoryDomain;
import com.peoplespheres.domain.PsoTypeDomain;
import com.peoplespheres.entites.PsoTypeEntity;

// Spring imports
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

// J2EE imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Mapper to switch from PSO type DTO, to domain instance and entity */
public final class PsoTypeMapper {
    /** Private default constructor to prevent instantiation */
    private PsoTypeMapper() {}

    /** Mapping a psoType entity to a psoType domain instance.
     *
     * @param psoTypeEntity The psoType entity to map to a psoType domain instance.
     * @return ScheduleDomain - The psoType domain instance mapped from the input psoType entity.
     */
    public static @NonNull PsoTypeDomain  fromEntityToDomain(@NonNull final PsoTypeEntity psoTypeEntity) {
        // Building a new PSO domain instance
        final PsoTypeDomain psoTypeDomain = new PsoTypeDomain();

        // Mapping the properties from the entity to the domain instance
        BeanUtils.copyProperties(psoTypeEntity, psoTypeDomain);
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean areFieldsLoaded = persistentUtil.isLoaded(psoTypeEntity, "fields");
        if (areFieldsLoaded && (null != psoTypeEntity.getFields())) {
            final Set<FieldCategoryDomain> fieldCategoryDomains = psoTypeEntity.getFields().stream().filter(Objects::nonNull).map(field -> {
                final boolean isFieldCategoryLoaded = persistentUtil.isLoaded(field, "category");
                if ( ! isFieldCategoryLoaded) return null;
                final FieldCategoryDomain fieldCategoryDomain = new FieldCategoryDomain();
                fieldCategoryDomain.setId(field.getCategory().getId());
                fieldCategoryDomain.setAlias(field.getCategory().getAlias());
                fieldCategoryDomain.setName(field.getCategory().getName());
                return fieldCategoryDomain;
            }).collect(Collectors.toSet());
            psoTypeDomain.setFieldCategories(fieldCategoryDomains);
        }

        // Returning the mapped PSO type domain instance
        return psoTypeDomain;
    }

    /** Mapper to convert a PSO type domain instance to a PSO DTO.
     *
     * @param psoTypeDomain The field domain instance to map/convert to a field DTO.
     * @return FieldDTO The field DTO result from the conversion of the domain instance.
     */
    public static @lombok.NonNull PsoTypeDTO fromDomainToDTO(@lombok.NonNull final PsoTypeDomain psoTypeDomain) {
        // Building the associated PSO type DTO
        final PsoTypeDTO psoTypeDTO = new PsoTypeDTO();

        // Mapping the properties from the domain instance to the DTO
        BeanUtils.copyProperties(psoTypeDomain, psoTypeDTO);
        if (null != psoTypeDomain.getFieldCategories()) {
            final Set<FieldCategoryDTO> fieldCategoryDTOs = psoTypeDomain.getFieldCategories().stream().filter(Objects::nonNull).map(fieldCategory -> {
                final FieldCategoryDTO fieldCategoryDTO = new FieldCategoryDTO();
                fieldCategoryDTO.setAlias(fieldCategory.getAlias());
                fieldCategoryDTO.setName(fieldCategory.getName());
                return fieldCategoryDTO;
            }).collect(Collectors.toSet());
            psoTypeDTO.setFieldCategories(fieldCategoryDTOs);
        }

        // Returning the mapped DTO
        return psoTypeDTO;
    }
}
