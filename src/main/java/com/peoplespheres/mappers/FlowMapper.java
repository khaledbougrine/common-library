package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.FlowDTO;
import com.peoplespheres.dto.FlowFullDTO;
import com.peoplespheres.dto.MappingFullDTO;
import com.peoplespheres.dto.ScheduleFullDTO;
import com.peoplespheres.domain.*;
import com.peoplespheres.entites.FlowEntity;
import com.peoplespheres.entites.PopulationEntity;

// Lombok imports
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

// J2EE imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public final class FlowMapper {
    /** Private default constructor to prevent instantiation */
    private FlowMapper() {}

    /** Mapping flow entity to flow domain instance.
     *
     * @param dataFlowEntity The flow entity to map to a flow domain instance.
     * @return FlowDomain - The flow domain instance mapped from the flow entity.
     */
    public static @NonNull FlowDomain fromEntityToDomain(@NonNull final FlowEntity dataFlowEntity) {
        final FlowDomain flowDomain = new FlowDomain();
        BeanUtils.copyProperties(dataFlowEntity, flowDomain);

        // Convert populations if necessary
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean arePopulationsLoaded = persistentUtil.isLoaded(dataFlowEntity, "populations");
        if ( arePopulationsLoaded && ( dataFlowEntity.getPopulations() != null)) {
            final Set<String> targetPopulations = dataFlowEntity.getPopulations().stream().map(PopulationEntity::getName).collect(Collectors.toSet());
            flowDomain.setTargetPopulations(targetPopulations);
        }

        // Convert client if necessary
        final boolean isClientLoaded = persistentUtil.isLoaded(dataFlowEntity, "client");
        if (isClientLoaded && (dataFlowEntity.getClient() != null)) {
            flowDomain.setClientId(dataFlowEntity.getClient().getId());
        }

        // Convert mappings if necessary
        final boolean areMappingsLoaded = persistentUtil.isLoaded(dataFlowEntity, "mappings");
        if (areMappingsLoaded && (dataFlowEntity.getMappings() != null)) {
            final Set<MappingDomain> mappingDomains = dataFlowEntity.getMappings().stream().map(MappingMapper::fromEntityToDomain).collect(Collectors.toSet());
            flowDomain.setMappings(mappingDomains);
        }

        // Convert schedule if necessary
        final boolean areSchedulesLoaded = persistentUtil.isLoaded(dataFlowEntity, "schedules");
        if (areSchedulesLoaded && (dataFlowEntity.getSchedules() != null)) {
            final Set<ScheduleDomain> scheduleDomains = dataFlowEntity.getSchedules().stream().map(ScheduleMapper::fromEntityToDomain).collect(Collectors.toSet());
            flowDomain.setSchedules(scheduleDomains);
        }

        // Convert connector if necessary
        final boolean isConnectorLoaded = persistentUtil.isLoaded(dataFlowEntity, "connector");
        if (isConnectorLoaded && (dataFlowEntity.getConnector() != null)) {
            flowDomain.setConnectorId(dataFlowEntity.getConnector().getId());
        }

        // Convert module if necessary
        final boolean isModuleLoaded = persistentUtil.isLoaded(dataFlowEntity, "module");
        if (isModuleLoaded && (dataFlowEntity.getModule() != null)) {
            flowDomain.setModuleId(dataFlowEntity.getModule().getId());
        }

        // Returned the converted flow domain instance
        return flowDomain;
    }

    /** Mapping flow entity to flow domain instance.
     *
     * @param flowDomain The flow domain instance to map to a flow entity.
     * @return FlowEntity - The flow entity mapped from the flow domain instance.
     */
    public static @NonNull FlowEntity fromDomainToEntity(@NonNull final FlowDomain flowDomain) {
        final FlowEntity dataFlowEntity = new FlowEntity();
        BeanUtils.copyProperties(flowDomain, dataFlowEntity);
        return dataFlowEntity;
    }

    /** Mapping flow DTO to flow domain instance.
     *
     * @param flowDTO The flow DTO to map to a flow domain instance.
     * @return FlowDomain - The flow domain instance mapped from the flow DTO.
     */
    public static @NonNull FlowDomain fromDtoToDomain(@NonNull final FlowDTO flowDTO) {
        final FlowDomain flowDomain = new FlowDomain();
        BeanUtils.copyProperties(flowDTO, flowDomain);
        flowDomain.setType(flowDTO.getType());
        flowDomain.setExecutionStatus(flowDTO.getExecutionStatus());
        flowDomain.setExecutionType(flowDTO.getExecutionType());
        flowDomain.setMappingStatus(flowDTO.getMappingStatus());
        flowDomain.setStatus(flowDTO.getStatus());
        if (flowDTO.getExecutionStatusDate() != null) {
            flowDomain.setExecutionStatusDate(OffsetDateTime.parse(flowDTO.getExecutionStatusDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        return flowDomain;
    }

    /** Mapping flow DTO to flow domain instance.
     *
     * @param flowDomain The flow domain instance to map to a flow DTO.
     * @return FlowDTO - The flow DTO mapped from the flow domain instance.
     */
    public static @NonNull FlowDTO fromDomainToDto(@NonNull final FlowDomain flowDomain) {
        final FlowDTO flowDTO = new FlowDTO();
        BeanUtils.copyProperties(flowDomain, flowDTO);
        flowDTO.setType(flowDomain.getType());
        flowDTO.setExecutionStatus(flowDomain.getExecutionStatus());
        flowDTO.setExecutionType(flowDomain.getExecutionType());
        flowDTO.setMappingStatus(flowDomain.getMappingStatus());
        flowDTO.setStatus(flowDomain.getStatus());
        flowDTO.setExecutionStatusDate((flowDomain.getExecutionStatusDate() != null) ? flowDomain.getExecutionStatusDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null);
        flowDTO.setClientId(flowDomain.getClientId());
        flowDTO.setConnectorId(flowDomain.getConnectorId());
        flowDTO.setModuleId(flowDomain.getModuleId());
        flowDTO.setTargetPopulations(flowDomain.getTargetPopulations());
        if ((flowDomain.getSchedules() != null) && ( ! flowDomain.getSchedules().isEmpty())) {
            final Set<Long> scheduleIds = flowDomain.getSchedules().stream().map(ScheduleDomain::getId).collect(Collectors.toSet());
            flowDTO.setScheduleIds(scheduleIds);
        }
        return flowDTO;
    }

    /** Mapping flow DTO to flow domain instance.
     *
     * @param flowDomain The flow domain instance to map to a flow DTO.
     * @return FlowDTO - The flow DTO mapped from the flow domain instance.
     */
    public static @NonNull FlowFullDTO fromDomainToFullDto(@NonNull final FlowDomain flowDomain) {
        final FlowFullDTO flowFullDTO = new FlowFullDTO();
        BeanUtils.copyProperties(flowDomain, flowFullDTO);

        if (flowDomain.getSchedules() != null && !flowDomain.getSchedules().isEmpty()) {
            List<ScheduleFullDTO> scheduleFullDTOList = flowDomain.getSchedules().stream().map(ScheduleMapper::fromDomainToFullDTO).toList();
            flowFullDTO.setSchedules(scheduleFullDTOList);
        }

        if (flowDomain.getMappings() != null && !flowDomain.getMappings().isEmpty()) {
            List<MappingFullDTO> mappingFullDTOS = flowDomain.getMappings().stream().map(MappingMapper::fromDomainToFullDTO).toList();
            flowFullDTO.setMappings(mappingFullDTOS);
        }

        if (flowDomain.getTargetPopulations() != null && !flowDomain.getTargetPopulations().isEmpty()) {
            Set<String> populations = flowDomain.getTargetPopulations();
            flowFullDTO.setTargetPopulations(populations.stream().toList());
        }

        return flowFullDTO;
    }

    public static @NonNull FlowDomain fromFullDtoToDomain(@NonNull final FlowFullDTO flowFullDTO) {
        final FlowDomain flowDomain = new FlowDomain();
        BeanUtils.copyProperties(flowFullDTO, flowDomain);
        flowDomain.setType(flowFullDTO.getType());
        flowDomain.setExecutionStatus(flowFullDTO.getExecutionStatus());
        flowDomain.setExecutionType(flowFullDTO.getExecutionType());
        flowDomain.setMappingStatus(flowFullDTO.getMappingStatus());
        flowDomain.setStatus(flowFullDTO.getStatus());
        if ((flowFullDTO.getSchedules() != null) && ( ! flowFullDTO.getSchedules().isEmpty())) {
            final Set<ScheduleDomain> scheduleDomains = flowFullDTO.getSchedules().stream().map(ScheduleMapper::fromFullDtoToDomain).collect(Collectors.toSet());
            flowDomain.setSchedules(scheduleDomains);
        }
        if ((flowFullDTO.getMappings() != null) && ( ! flowFullDTO.getMappings().isEmpty())) {
            final Set<MappingDomain> mappingDomains = flowFullDTO.getMappings().stream().map(MappingMapper::fromFullDtoToDomain).collect(Collectors.toSet());
            flowDomain.setMappings(mappingDomains);
        }
        if ((flowFullDTO.getTargetPopulations() != null) && ( ! flowFullDTO.getTargetPopulations().isEmpty())) {
            flowDomain.setTargetPopulations(new HashSet<>(flowFullDTO.getTargetPopulations()));
        }

        // Returning the flow domain
        return flowDomain;
    }
}
