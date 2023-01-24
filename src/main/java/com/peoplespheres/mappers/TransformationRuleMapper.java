package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.TransformationRuleDTO;
import com.peoplespheres.domain.TransformationRuleDomain;

// Lombok imports
import com.peoplespheres.entites.TransformationRuleEntity;
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

/** Mapper from transformation rule entity to transformation rule domain instance */
public final class TransformationRuleMapper {
    /** Private default constructor to prevent instantiation */
    private TransformationRuleMapper() {}

    /** Mapper transform data source entity into data source domain instance.
     *
     * @param transformationRuleEntity The transformation rule entity to map into a transformation rule domain instance.
     * @return TransformationRuleDomain The transformation rule domain instance mapped from the transformation rule entity.
     */
    public static @NonNull TransformationRuleDomain fromEntityToDomain(@NonNull final TransformationRuleEntity transformationRuleEntity) {
        final TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
        BeanUtils.copyProperties(transformationRuleEntity, transformationRuleDomain);
        return transformationRuleDomain;
    }

    /** Mapper transform data source domain instance into data source entity.
     *
     * @param transformationRuleDomain The transformation rule entity to map into a transformation rule domain instance.
     * @return TransformationRuleEntity The transformation rule entity mapped from the transformation rule domain instance.
     */
    public static @NonNull TransformationRuleEntity fromDomainToEntity(@NonNull final TransformationRuleDomain transformationRuleDomain) {
        final TransformationRuleEntity transformationRuleEntity = new TransformationRuleEntity();
        BeanUtils.copyProperties(transformationRuleDomain, transformationRuleEntity);
        return transformationRuleEntity;
    }

    /** Mapper transform data source entity into data source domain instance.
     *
     * @param transformationRuleDTO The transformation rule entity to map into a transformation rule domain instance.
     * @return TransformationRuleDomain The transformation rule domain instance mapped from the transformation rule entity.
     */
    public static @NonNull TransformationRuleDomain fromDtoToDomain(@NonNull final TransformationRuleDTO transformationRuleDTO) {
        final TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
        BeanUtils.copyProperties(transformationRuleDTO, transformationRuleDomain);
        return transformationRuleDomain;
    }

    /** Mapper transform data source domain instance into data source DTO.
     *
     * @param transformationRuleDomain The transformation rule entity to map into a transformation rule domain instance.
     * @return TransformationRuleDTO The transformation rule DTO mapped from the transformation rule domain instance.
     */
    public static @NonNull TransformationRuleDTO fromDomainToDTO(@NonNull final TransformationRuleDomain transformationRuleDomain) {
        final TransformationRuleDTO transformationRuleDTO = new TransformationRuleDTO();
        BeanUtils.copyProperties(transformationRuleDomain, transformationRuleDTO);
        return transformationRuleDTO;
    }
}
