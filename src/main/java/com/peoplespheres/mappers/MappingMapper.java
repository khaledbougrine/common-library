package com.peoplespheres.mappers;

// Local imports

import com.peoplespheres.dto.*;
import com.peoplespheres.domain.*;
import com.peoplespheres.entites.MappingEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.*;

/** Mapper to switch from schedule DTO, to domain instance and entity */
public final class MappingMapper {
    /** Private default constructor to prevent instantiation */
    private MappingMapper() {}

    /** Mapping schedule DTO to schedule domain instance.
     *
     * @param mappingDTO The schedule DTO to map to schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the schedule DTO.
     */
    public static @NonNull MappingDomain fromDTOToDomain(@NonNull final MappingDTO mappingDTO) {
        final MappingDomain mappingDomain = new MappingDomain();
        BeanUtils.copyProperties(mappingDTO, mappingDomain);
        return mappingDomain;
    }

    /** Mapping a schedule domain instance to a schedule DTO.
     *
     * @param mappingDomain The schedule domain instance to map to a schedule DTO.
     * @return ScheduleDTO - The schedule DTO mapped from the input schedule domain instance.
     */
    public static @NonNull MappingDTO fromDomainToDTO(@NonNull final MappingDomain mappingDomain) {
        final MappingDTO mappingDTO = new MappingDTO();
        BeanUtils.copyProperties(mappingDomain, mappingDTO);

        // Managing the mapping data source
        if (mappingDomain.getDataSource() != null) {
            final DataSourceDTO dataSourceDTO = DataSourceMapper.fromDomainToDTO(mappingDomain.getDataSource());
            mappingDTO.setDataSource(dataSourceDTO);
        }

        // Managing the mapping data source
        if (mappingDomain.getDataTarget() != null) {
            final DataTargetDTO dataTargetDTO = DataTargetMapper.fromDomainToDTO(mappingDomain.getDataTarget());
            mappingDTO.setDataTarget(dataTargetDTO);
        }

        // Managing the mapping transformations
        if ((mappingDomain.getTransformations() != null) && ( ! mappingDomain.getTransformations().isEmpty())) {
            List<TransformationDTO> transformationDTOCollection = new ArrayList<>(mappingDomain.getTransformations().stream().map(TransformationMapper::fromDomainToDto).toList());
            Collections.sort(transformationDTOCollection, Comparator.comparing(TransformationDTO::getPosition));
            mappingDTO.setTransformations(transformationDTOCollection);
        }

        // Returning the mapping DTO built
        return mappingDTO;
    }

    /** Mapping a schedule domain instance to a schedule DTO.
     *
     * @param mappingDomain The schedule domain instance to map to a schedule DTO.
     * @return ScheduleDTO - The schedule DTO mapped from the input schedule domain instance.
     */
    public static @NonNull MappingFullDTO fromDomainToFullDTO(@NonNull final MappingDomain mappingDomain) {
        final MappingFullDTO mappingFullDTO = new MappingFullDTO();
        BeanUtils.copyProperties(mappingDomain, mappingFullDTO);

        if (mappingDomain.getDataSource() != null) {
            mappingFullDTO.setDataSource(DataSourceMapper.fromDomainToFullDTO(mappingDomain.getDataSource()));
        }

        if (mappingDomain.getDataTarget() != null) {
            mappingFullDTO.setDataTarget(DataTargetMapper.fromDomainToFullDTO(mappingDomain.getDataTarget()));
        }

        if (mappingDomain.getTransformations() != null && !mappingDomain.getTransformations().isEmpty()) {
            List<TransformationFullDTO> transformationFullDTO = new ArrayList<>(mappingDomain.getTransformations().stream().map(TransformationMapper::fromDomainToFullDto).toList());
            Collections.sort(transformationFullDTO, Comparator.comparing(TransformationFullDTO::getPosition));
            mappingFullDTO.setTransformations(transformationFullDTO);
        }
        return mappingFullDTO;
    }

    /** Mapping schedule DTO to schedule domain instance.
     *
     * @param mappingFullDTO The schedule DTO to map to schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the schedule DTO.
     */
    public static @NonNull MappingDomain fromFullDtoToDomain(@NonNull final MappingFullDTO mappingFullDTO) {
        final MappingDomain mappingDomain = new MappingDomain();
        BeanUtils.copyProperties(mappingFullDTO, mappingDomain);
        if (mappingFullDTO.getDataSource() != null) {
            DataSourceDomain dataSourceDomain = DataSourceMapper.fromFullDtoToDomain(mappingFullDTO.getDataSource());
            mappingDomain.setDataSource(dataSourceDomain);
        }

        if (mappingFullDTO.getDataTarget() != null) {
            DataTargetDomain dataTargetDomain = DataTargetMapper.fromFullDtoToDomain(mappingFullDTO.getDataTarget());
            mappingDomain.setDataTarget(dataTargetDomain);
        }

        if (mappingFullDTO.getTransformations() != null && !mappingFullDTO.getTransformations().isEmpty()) {
            List<TransformationDomain> transformationDomains = mappingFullDTO.getTransformations().stream().map(TransformationMapper::fromFullDtoToDomain).toList();
            mappingDomain.setTransformations(transformationDomains);
        }

        return mappingDomain;
    }

    /** Mapping schedule DTO to schedule domain instance.
     *
     * @param mappingEntity The schedule DTO to map to schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the schedule DTO.
     */
    public static @NonNull MappingDomain fromEntityToDomain(@NonNull final MappingEntity mappingEntity) {
        final MappingDomain mappingDomain = new MappingDomain();
        BeanUtils.copyProperties(mappingEntity, mappingDomain);
        mappingDomain.setFlowId(mappingEntity.getFlow().getId());

        // Converting if necessary the mapping data source
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isDataSourceLoaded = persistentUtil.isLoaded(mappingEntity, "source");
        if (isDataSourceLoaded && (mappingEntity.getSource() != null)) {
            DataSourceDomain dataSourceDomain = DataSourceMapper.fromEntityToDomain(mappingEntity.getSource());
            mappingDomain.setDataSource(dataSourceDomain);
        }

        // Converting if necessary the mapping data source
        final boolean isDataTargetLoaded = persistentUtil.isLoaded(mappingEntity, "target");
        if (isDataTargetLoaded && (mappingEntity.getTarget() != null)) {
            DataTargetDomain dataTargetDomain = DataTargetMapper.fromEntityToDomain(mappingEntity.getTarget());
            mappingDomain.setDataTarget(dataTargetDomain);
        }

        // Converting if necessary the mapping transformations
        final boolean areTransformationsLoaded = persistentUtil.isLoaded(mappingEntity, "transformations");
        if (areTransformationsLoaded && (mappingEntity.getTransformations() != null)) {
            List<TransformationDomain> transformationDomains = mappingEntity.getTransformations().stream().map(TransformationMapper::fromEntityToDomain).toList();
            mappingDomain.setTransformations(transformationDomains);
        }

        // Returning the converted mapping
        return mappingDomain;
    }
}
