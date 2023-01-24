package com.peoplespheres.mappers;

// Local imports

import com.peoplespheres.dto.TransformationDTO;
import com.peoplespheres.dto.TransformationFullDTO;
import com.peoplespheres.dto.TransformationRuleDTO;
import com.peoplespheres.entites.TransformationEntity;
import com.peoplespheres.entites.TransformationRuleEntity;
import com.peoplespheres.domain.TransformationDomain;
import com.peoplespheres.domain.TransformationRuleDomain;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.Optional;

/** Mapper from transformation entity to transformation domain instance */
public final class TransformationMapper {
    /** Private default constructor to prevent instantiation */
    private TransformationMapper() {}

    /** Mapper transform data source entity into data source domain instance.
     *
     * @param transformationEntity The transformation entity to map into a transformation domain instance.
     * @return TransformationDomain - The transformation domain instance mapped from the transformation entity.
     */
    public static @NonNull TransformationDomain fromEntityToDomain(@NonNull final TransformationEntity transformationEntity) {
        final TransformationDomain transformationDomain = new TransformationDomain();
        BeanUtils.copyProperties(transformationEntity, transformationDomain);

        // Converting if necessary the transformation rule
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isTransformationRuleLoaded = persistentUtil.isLoaded(transformationEntity, "transformationRule");
        if (isTransformationRuleLoaded && (transformationEntity.getTransformationRule() != null)) {
            final TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
            BeanUtils.copyProperties(transformationEntity.getTransformationRule(), transformationRuleDomain);
            transformationDomain.setTransformationRule(transformationRuleDomain);
        }

        // Converting if necessary the transformation rule
        final boolean isModuleLoaded = persistentUtil.isLoaded(transformationEntity, "module");
        if (isModuleLoaded && (transformationEntity.getModule() != null)) {
            transformationDomain.setModuleId(transformationEntity.getModule().getId());
        }
        return transformationDomain;
    }

    /** Mapper transform data source domain instance into data source entity.
     *
     * @param transformationDomain The transformation domain instance to map into a transformation entity.
     * @return TransformationDomain - The transformation entity mapped from the transformation domain instance.
     */
    public static @NonNull TransformationEntity fromDomainToEntity(@NonNull final TransformationDomain transformationDomain) {
        final TransformationEntity transformationEntity = new TransformationEntity();
        BeanUtils.copyProperties(transformationDomain, transformationEntity);
        if (transformationDomain.getTransformationRule() != null) {
            final TransformationRuleEntity transformationRuleEntity = new TransformationRuleEntity();
            BeanUtils.copyProperties(transformationDomain.getTransformationRule(), transformationRuleEntity);
            transformationEntity.setTransformationRule(transformationRuleEntity);
        }
        return transformationEntity;
    }

    /** Mapper transform data source domain instance into data source DTO.
     *
     * @param transformationDomain The transformation domain instance to map into a transformation DTO.
     * @return TransformationDTO - The transformation DTO mapped from the transformation domain instance.
     */
    public static @NonNull TransformationDTO fromDomainToDto(@NonNull final TransformationDomain transformationDomain) {
        final TransformationDTO transformationDTO = new TransformationDTO();
        BeanUtils.copyProperties(transformationDomain, transformationDTO);
        transformationDTO.setModuleId(transformationDomain.getModuleId());
        if (transformationDomain.getTransformationRule() != null) {
            final TransformationRuleDTO transformationRuleDTO = new TransformationRuleDTO();
            BeanUtils.copyProperties(transformationDomain.getTransformationRule(), transformationRuleDTO);
            transformationDTO.setTransformationRule(transformationRuleDTO);
        }
        return transformationDTO;
    }

    /** Mapper transform data source domain instance into data source DTO.
     *
     * @param transformationDomain The transformation domain instance to map into a transformation DTO.
     * @return TransformationDTO - The transformation DTO mapped from the transformation domain instance.
     */
    public static @NonNull TransformationFullDTO fromDomainToFullDto(@NonNull final TransformationDomain transformationDomain) {
        final TransformationFullDTO transformationFullDTO = new TransformationFullDTO();
        BeanUtils.copyProperties(transformationDomain, transformationFullDTO);
        if (transformationDomain.getTransformationRule() != null) {
            final TransformationRuleDTO transformationRuleDTO = new TransformationRuleDTO();
            BeanUtils.copyProperties(transformationDomain.getTransformationRule(), transformationRuleDTO);
            transformationFullDTO.setTransformationRule(transformationRuleDTO);
        }
        return transformationFullDTO;
    }

    /** Mapper transform data source DTO into data source domain instance.
     *
     * @param transformationDTO The transformation domain instance to map into a transformation DTO.
     * @return TransformationDomain - The transformation domain instance mapped from the transformation DTO.
     */
    public static @NonNull TransformationDomain fromDtoToDomain(@NonNull final TransformationDTO transformationDTO) {
        final TransformationDomain transformationDomain = new TransformationDomain();
        BeanUtils.copyProperties(transformationDTO, transformationDomain);
        if (transformationDTO.getTransformationRule() != null) {
            TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
            BeanUtils.copyProperties(transformationDomain.getTransformationRule(), transformationRuleDomain);
            transformationDomain.setTransformationRule(transformationRuleDomain);
        }
        return transformationDomain;
    }

    public static @NonNull TransformationDomain fromFullDtoToDomain(@NonNull final TransformationFullDTO transformationFullDTO) {
        final TransformationDomain transformationDomain = new TransformationDomain();
        BeanUtils.copyProperties(transformationFullDTO, transformationDomain);
        if (transformationFullDTO.getTransformationRule() != null) {
            TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
            // add the Optional to pass the quality test
            BeanUtils.copyProperties(Optional.ofNullable(transformationFullDTO.getTransformationRule()).orElse(new TransformationRuleDTO()), transformationRuleDomain);
            transformationDomain.setTransformationRule(transformationRuleDomain);
        }
        return transformationDomain;
    }
}
