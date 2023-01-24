package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.ConnectorDTO;
import com.peoplespheres.dto.ConnectorFullDTO;
import com.peoplespheres.dto.FlowFullDTO;
import com.peoplespheres.domain.*;
import com.peoplespheres.entites.ConnectorEntity;

// Spring imports
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

// J2EE imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConnectorMapper {
    /** Private default constructor to prevent instantiation */
    private ConnectorMapper() {}

    /** Mapping connector entity to associated connector domain.
     *
     * @param connectorEntity The connector entity to map.
     * @return ConnectorDomain - The associated connector domain instance mapped to the input connector entity.
     */
    public static @NonNull ConnectorDomain fromEntityToDomain(@NonNull final ConnectorEntity connectorEntity) {
        final ConnectorDomain connectorDomain = new ConnectorDomain();
        BeanUtils.copyProperties(connectorEntity, connectorDomain);

        // Convert flows
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean areFlowsLoaded = persistentUtil.isLoaded(connectorEntity, "flows");
        if (areFlowsLoaded && (connectorEntity.getFlows() != null)) {
            final Set<FlowDomain> flowDomains = connectorEntity.getFlows().stream().map(FlowMapper::fromEntityToDomain).collect(Collectors.toSet());
            connectorDomain.setFlows(flowDomains);
        }

        // Convert client
        final boolean isClientLoaded = persistentUtil.isLoaded(connectorEntity, "client");
        if (isClientLoaded && (connectorEntity.getClient() != null)) {
            connectorDomain.setClientId(connectorEntity.getClient().getId());
        }

        // Convert module
        final boolean isModuleLoaded = persistentUtil.isLoaded(connectorEntity, "module");
        if (isModuleLoaded && (connectorEntity.getModule() != null)) {
            connectorDomain.setModule(ModuleMapper.fromEntityToDomain(connectorEntity.getModule()));
        }
        return connectorDomain;
    }

    /** Mapping connector domain instance to associated connector entity.
     *
     * @param connectorDomain The connector domain to map.
     * @return ConnectorEntity - The associated connector entity mapped to the input connector domain instance.
     */
    public static @NonNull ConnectorEntity fromDomainToEntity(@NonNull final ConnectorDomain connectorDomain) {
        final ConnectorEntity connectorEntity = new ConnectorEntity();
        BeanUtils.copyProperties(connectorDomain, connectorEntity);
        return connectorEntity;
    }

    /** Mapping connector DTO to associated connector domain instance.
     *
     * @param connectorDTO The connector entity to map.
     * @return ConnectorDomain - The associated connector domain instance mapped to the input connector entity.
     */
    public static @NonNull ConnectorDomain fromDtoToDomain(@NonNull final ConnectorDTO connectorDTO) {
        final ConnectorDomain connectorDomain = new ConnectorDomain();
        BeanUtils.copyProperties(connectorDTO, connectorDomain);
        connectorDomain.setType(connectorDTO.getTypeCode());
        connectorDomain.setIsActive(true);

        // Returning the connector domain instance
        return connectorDomain;
    }

    /** Mapping connector domain instance to associated connector DTO.
     *
     * @param connectorDomain The connector domain to map.
     * @return ConnectorDTO - The associated connector DTO mapped to the input connector domain instance.
     */
    public static @NonNull ConnectorDTO fromDomainToDto(@NonNull final ConnectorDomain connectorDomain) {
        final ConnectorDTO connectorDTO = new ConnectorDTO();
        BeanUtils.copyProperties(connectorDomain, connectorDTO);
        connectorDTO.setTypeCode(connectorDomain.getType());
        if ((connectorDomain.getFlows() != null) && ( ! connectorDomain.getFlows().isEmpty())) {
            final List<Long> flowIds = connectorDomain.getFlows().stream().map(FlowDomain::getId).toList();
            connectorDTO.setFlowIds(flowIds);
        }

        // Convert client
        if (connectorDomain.getClientId() != null) {
            connectorDTO.setClientId(connectorDomain.getClientId());
        }

        // Convert module
        if (connectorDomain.getModule() != null) {
            connectorDTO.setModuleId(connectorDomain.getModule().getId());
        }
        return connectorDTO;
    }

    /** Mapping connector domain instance to associated connector DTO.
     *
     * @param connectorDomain The connector domain to map.
     * @return ConnectorDTO - The associated connector DTO mapped to the input connector domain instance.
     */
    public static @NonNull ConnectorFullDTO fromDomainToFullDto(@NonNull final ConnectorDomain connectorDomain) {
        final ConnectorFullDTO connectorDTO = new ConnectorFullDTO();
        BeanUtils.copyProperties(connectorDomain, connectorDTO);
        connectorDTO.setType(connectorDomain.getType());

        if (connectorDomain.getFlows() != null && !connectorDomain.getFlows().isEmpty()) {
            connectorDTO.setFlows(connectorDomain.getFlows().stream().map(FlowMapper::fromDomainToFullDto).collect(Collectors.toSet()));
        }

        return connectorDTO;
    }


    /** Mapping connector domain instance to associated connector DTO.
     *
     * @param connectorDomain The connector domain to map.
     * @return ConnectorDTO - The associated connector DTO mapped to the input connector domain instance.
     */
    public static @NonNull ConnectorFullDTO fromDomainToDtoFull(@NonNull final ConnectorDomain connectorDomain) {
        final ConnectorFullDTO connectorDTO = new ConnectorFullDTO();
        BeanUtils.copyProperties(connectorDomain, connectorDTO);
        connectorDTO.setType(connectorDomain.getType());
        if ((connectorDomain.getFlows() != null) && ( ! connectorDomain.getFlows().isEmpty())) {
            final Set<FlowFullDTO> flows = connectorDomain.getFlows().stream().map(FlowMapper::fromDomainToFullDto).collect(Collectors.toSet());
            connectorDTO.setFlows(flows);
        }
        return connectorDTO;
    }

    /** Mapping connector DTO to associated connector domain instance.
     *
     * @param connectorFullDTO The connector entity to map.
     * @return ConnectorDomain - The associated connector domain instance mapped to the input connector entity.
     */
    public static @NonNull ConnectorDomain fromFullDtoToDomain(@NonNull final ConnectorFullDTO connectorFullDTO) {
        final ConnectorDomain connectorDomain = new ConnectorDomain();
        BeanUtils.copyProperties(connectorFullDTO, connectorDomain);
        connectorDomain.setType(connectorFullDTO.getType());
        connectorDomain.setIsActive(true);

        // If we have flows in the connector, retrieving them from the datasource
        if ((connectorFullDTO.getFlows() != null) && ( ! connectorFullDTO.getFlows().isEmpty())) {
            final Set<FlowDomain> flowDomains = connectorFullDTO.getFlows().stream().map(FlowMapper::fromFullDtoToDomain).collect(Collectors.toSet());
            connectorDomain.setFlows(flowDomains);
        }

        // Returning the connector domain instance
        return connectorDomain;
    }
}
