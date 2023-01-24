package mappers;

// Local imports

import com.peoplespheres.domain.ClientDomain;
import com.peoplespheres.domain.ConnectorDomain;
import com.peoplespheres.domain.FlowDomain;
import com.peoplespheres.domain.ModuleDomain;
import com.peoplespheres.dto.ConnectorDTO;
import com.peoplespheres.dto.ConnectorFullDTO;
import com.peoplespheres.entites.*;
import com.peoplespheres.enums.ConnectorTypeEnum;
import com.peoplespheres.mappers.ConnectorMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Unit test for the behaviour of the connector mapper")
@SpringBootTest(classes = WebMvcConfig.class)
class ConnectorMapperTest {

    private ConnectorEntity connectorEntity;

    private Collection<FlowEntity> flowEntityList;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        connectorEntity = helperClass.getEntity_Helper("Connectors/connectore_item.json", ConnectorEntity.class);
        flowEntityList = helperClass.getEntities_Helper("Flows/flow.json", FlowEntity.class);
        connectorEntity.setFlows(new HashSet<>(flowEntityList));
    }

    /** Unique identifier of the client orchestrate used in this test */
    private static final String CLIENT_ID = "33333330-3336-3331-2d33-3633312d3336"; // Orchestrate

    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between connector entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setName("orchestrate");
        final FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(1L);
        flowEntity.setName("Legal entity");
        final ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setId(8765L);
        moduleEntity.setName("Cornerstone legal entity");
        final ConnectorEntity connectorEntity = new ConnectorEntity();
        connectorEntity.setId(1L);
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        connectorEntity.setAuditEmbedded(auditEmbedded);
        connectorEntity.setClient(clientEntity);
        connectorEntity.getFlows().add(flowEntity);
        connectorEntity.setModule(moduleEntity);
        connectorEntity.setName("Cornerstone");
        connectorEntity.setType(ConnectorTypeEnum.INOUT);
        connectorEntity.setIsActive(true);

        // then
        final ConnectorDomain connectorDomain = ConnectorMapper.fromEntityToDomain(connectorEntity);
        final ConnectorEntity newConnectorEntity = ConnectorMapper.fromDomainToEntity(connectorDomain);

        // should
        AssertionsForClassTypes.assertThat(newConnectorEntity).as("Connector entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newConnectorEntity.getId()).as("Connector id should remain the same").isEqualTo(connectorEntity.getId());
        AssertionsForClassTypes.assertThat(newConnectorEntity.getName()).as("Connector name should remain the same").isEqualTo(connectorEntity.getName());
        AssertionsForClassTypes.assertThat(newConnectorEntity.getType()).as("Connector type should remain the same").isEqualTo(connectorEntity.getType());
        AssertionsForClassTypes.assertThat(newConnectorEntity.getIsActive()).as("Connector active status should remain the same").isEqualTo(connectorEntity.getIsActive());
        AssertionsForClassTypes.assertThat(newConnectorEntity.getClient()).as("Connector client should remain null until completion with proxy bag").isNull();
        AssertionsForClassTypes.assertThat(newConnectorEntity.getModule()).as("Connector module should remain null until completion with proxy bag").isNull();
        AssertionsForInterfaceTypes.assertThat(newConnectorEntity.getFlows()).as("Connector flows should remain null until completion with proxy bag").isEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test mapping between connector DTOs and domain instances")
    void fromDomainToDTOBackToDomain_shouldRemainTheSame() {
        //given
        final FlowDomain flowDomain = new FlowDomain();
        flowDomain.setId(1L);
        final ClientDomain clientDomain = new ClientDomain();
        clientDomain.setId(CLIENT_ID);
        clientDomain.setName("orchestrate");
        final ModuleDomain moduleDomain = new ModuleDomain();
        moduleDomain.setId(8_765L);
        final ConnectorDomain connectorDomain = new ConnectorDomain();
        connectorDomain.setId(1L);
        connectorDomain.setClientId(clientDomain.getId());
        connectorDomain.setFlows(Set.of(flowDomain));
        connectorDomain.setModule(moduleDomain);
        connectorDomain.setName("Cornerstone");
        connectorDomain.setType(ConnectorTypeEnum.INOUT);

        // then
        final ConnectorDTO connectorDTO = ConnectorMapper.fromDomainToDto(connectorDomain);
        final ConnectorDomain newConnectorDomain = ConnectorMapper.fromDtoToDomain(connectorDTO);

        // should
        AssertionsForClassTypes.assertThat(newConnectorDomain).as("Connector domain instance should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newConnectorDomain.getId()).as("Connector id should remain the same").isEqualTo(connectorDomain.getId());
        AssertionsForClassTypes.assertThat(newConnectorDomain.getName()).as("Connector name should remain the same").isEqualTo(connectorDomain.getName());
        AssertionsForClassTypes.assertThat(newConnectorDomain.getType()).as("Connector type should remain the same").isEqualTo(connectorDomain.getType());
        AssertionsForClassTypes.assertThat(newConnectorDomain.getClientId()).as("Connector client should remain the same").isEqualTo("33333330-3336-3331-2d33-3633312d3336");

        // Associations will remain null until the completion with the proxy bag or the associated entity by the adapter
        AssertionsForInterfaceTypes.assertThat(newConnectorDomain.getFlows()).as("Connector flow ids should remain null until completion with the proxy bag").isNull();
        AssertionsForClassTypes.assertThat(newConnectorDomain.getModule()).as("Connector module id should remain null until completion with the proxy bag").isNull();
    }

    @Test
    @Order(3)
    @DisplayName("3 - Test mapping between connector domain and fullDto instances")
    void fromDomainToFullDto_BackToDomain_test() {

        ConnectorDomain connectorDomain =  ConnectorMapper.fromEntityToDomain(connectorEntity);
        ConnectorFullDTO connectorFullDTO =  ConnectorMapper.fromDomainToFullDto(connectorDomain);


        // Test mapping between Domain and fullDto instances
        assertThat(connectorDomain).usingRecursiveComparison().comparingOnlyFields(
                "id" , "name", "moduleId", "fieldType", "clientId",
                "created_at", "updated_at", "deleted_at", "version", "is_active").
                isEqualTo(connectorFullDTO);

        // Test mapping between FullDto and Domain instances
        assertThat(connectorFullDTO).usingRecursiveComparison().comparingOnlyFields(
                "id" , "name", "moduleId", "fieldType", "clientId", "created_at",
                "updated_at", "deleted_at", "version", "is_active").
                isEqualTo(ConnectorMapper.fromFullDtoToDomain(connectorFullDTO));

        //  Test the fromDomainToDtoFull methode
        assertThat(connectorDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id" , "name", "moduleId", "fieldType", "clientId", "created_at",
                        "updated_at", "deleted_at", "version", "is_active").
                isEqualTo(ConnectorMapper.fromDomainToDtoFull(connectorDomain));

    }

}