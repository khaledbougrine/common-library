package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.ClientDTO;
import com.peoplespheres.domain.ConnectorDomain;
import com.peoplespheres.domain.ClientDomain;
import com.peoplespheres.domain.ClientSettingDomain;
import com.peoplespheres.entites.ClientEntity;

// Spring imports
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

// J2EE imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ClientMapper {
    /** Private default constructor to prevent instantiation */
    private ClientMapper() {}

    /** Mapping client entity to associated client domain.
     *
     * @param clientEntity The client entity to map.
     * @return ConnectorDomain - The associated client domain instance mapped to the input client entity.
     */
    public static @NonNull ClientDomain fromEntityToDomain(@NonNull final ClientEntity clientEntity) {
        final ClientDomain clientDomain = new ClientDomain();
        BeanUtils.copyProperties(clientEntity, clientDomain);

        // Loading if necessary the connectors
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean areConnectorsLoaded = persistentUtil.isLoaded(clientEntity, "connectors");
        if (areConnectorsLoaded && (clientEntity.getConnectors() != null)) {
            final Set<ConnectorDomain> connectorDomains = clientEntity.getConnectors().stream().filter(Objects::nonNull).map(ConnectorMapper::fromEntityToDomain).collect(Collectors.toSet());
            clientDomain.setConnectors(connectorDomains);
        }

        // Loading if necessary the client settings
        final boolean areSettingsLoaded = persistentUtil.isLoaded(clientEntity, "clientSettings");
        if (areSettingsLoaded && (clientEntity.getClientSettings() != null)) {
            final Set<ClientSettingDomain> clientSettingDomains = clientEntity.getClientSettings().stream().filter(Objects::nonNull).map(ClientSettingMapper::fromEntityToDomain).collect(Collectors.toSet());
            clientDomain.setClientSettings(clientSettingDomains);
        }

        // Returning the converted entity
        return clientDomain;
    }

    /** Mapping client domain to associated client DTO.
     *
     * @param clientDomain The associated client domain instance to map to a DTO.
     * @return ClientDTO The client DTO mapped from the input client domain instance.
     */
    public static @NonNull ClientDTO fromDomainToDto(@NonNull final ClientDomain clientDomain) {
        final ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(clientDomain, clientDTO);
        if ((clientDomain.getConnectors() != null) && ( ! clientDomain.getConnectors().isEmpty())) {
            final List<Long> clientConnectorIds = clientDomain.getConnectors().stream().filter(Objects::nonNull).map(ConnectorDomain::getId).toList();
            clientDTO.setConnectorIds(clientConnectorIds);
        }
        if ((clientDomain.getClientSettings() != null) && ( ! clientDomain.getClientSettings().isEmpty())) {
            final List<Long> clientSettingIds = clientDomain.getClientSettings().stream().filter(Objects::nonNull).map(ClientSettingDomain::getId).toList();
            clientDTO.setSettingIds(clientSettingIds);
        }
        return clientDTO;
    }
}
