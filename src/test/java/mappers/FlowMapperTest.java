package mappers;

// Local imports

import com.peoplespheres.domain.ClientDomain;
import com.peoplespheres.domain.FlowDomain;
import com.peoplespheres.domain.ModuleDomain;
import com.peoplespheres.dto.FlowDTO;
import com.peoplespheres.dto.FlowFullDTO;
import com.peoplespheres.entites.*;
import com.peoplespheres.enums.ExecutionStatusEnum;
import com.peoplespheres.enums.ExecutionTypeEnum;
import com.peoplespheres.enums.FlowDirectionEnum;
import com.peoplespheres.enums.MappingStatusEnum;
import com.peoplespheres.mappers.FlowMapper;
import com.peoplespheres.mappers.MappingMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit test for the behaviour of the flow mapper")
@SpringBootTest(classes = WebMvcConfig.class)
class FlowMapperTest {
    /** Unique identifier of the client orchestrate used in this test */

    @Autowired
    private AdapterHelper helperClass;
    private Collection<ScheduleEntity> scheduleEntities;
    //private Collection<DataTargetEntity> dataTargetEntities;
    private Collection<MappingEntity> mappingEntities;
    private ModuleEntity moduleEntity;
    private FlowEntity flowEntity;
    private FlowDomain flowDomain;
    private FlowFullDTO flowFullDTO;
    private MappingEntity mappingEntity;


    @BeforeEach
    private void setUp() {
        flowEntity=helperClass.getEntity_Helper("Entities/flowEntity.json",FlowEntity.class);
        scheduleEntities= helperClass.getEntities_Helper("Entities/scheduleEntities.json",ScheduleEntity.class);
        mappingEntity = helperClass.getEntity_Helper("Entities/mappingEntity.json",MappingEntity.class);
        mappingEntity.setFlow(flowEntity);
       moduleEntity = helperClass.getEntity_Helper("Entities/moduleEntity_Item.json",ModuleEntity.class);
       flowEntity.setMappings(Set.of(mappingEntity));
       flowEntity.setSchedules(new HashSet<>(scheduleEntities));
       flowEntity.setModule(moduleEntity);
       flowDomain = FlowMapper.fromEntityToDomain(flowEntity);
       flowFullDTO= FlowMapper.fromDomainToFullDto(flowDomain);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Test mapping between flow entities and domain instances")
    void fromDomainToFullDto_Test(){
        assertThat(flowDomain).usingRecursiveComparison().comparingOnlyFields("id", "is_active", "created_at", "exe_status", "exe_status_date", "exe_type", "flowId", "mapping_status", "name", "status", "fieldType", "validated_at", "flow_version",
                "version", "updated_at", "is_active_version", "deleted_at").
                isEqualTo(flowFullDTO);

        assertThat(flowFullDTO).usingRecursiveComparison().comparingOnlyFields("id", "is_active", "created_at", "exe_status", "exe_status_date", "exe_type", "flowId", "mapping_status", "name", "status", "fieldType", "validated_at", "flow_version",
                        "version", "updated_at", "is_active_version", "deleted_at").
                isEqualTo(FlowMapper.fromFullDtoToDomain(flowFullDTO));





    }

    private static final String CLIENT_ID = "33333330-3336-3331-2d33-3633312d3336"; // Orchestrate
    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between flow entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setName("orchestrate");
        final ConnectorEntity connectorEntity = new ConnectorEntity();
        connectorEntity.setId(1L);
        connectorEntity.setName("Connector name");
        final PopulationEntity populationEntity = new PopulationEntity();
        populationEntity.setId(1L);
        populationEntity.setName("Population test");
        final FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(1L);
        flowEntity.setName("Legal entity");
        flowEntity.setFlowVersion("V1.1");
        flowEntity.setIsActiveVersion(true);
        flowEntity.setType(FlowDirectionEnum.IN);
        flowEntity.setClient(clientEntity);
        flowEntity.setPopulations(Set.of(populationEntity));
        flowEntity.setConnector(connectorEntity);
        flowEntity.setPsoType("usr");
        flowEntity.setIsActive(true);
        flowEntity.setExecutionStatus(ExecutionStatusEnum.RUNNING);
        flowEntity.setExecutionStatusDate(OffsetDateTime.now());
        flowEntity.setExecutionType(ExecutionTypeEnum.SCHEDULED);
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        flowEntity.setAuditEmbedded(auditEmbedded);
        flowEntity.setMappingStatus(MappingStatusEnum.CONFIRMED);
     //   flowEntity.setMappings(new HashSet<>(mappingEntities));
        flowEntity.setSchedules(new HashSet<>(scheduleEntities));
        flowEntity.setModule(moduleEntity);

        // then
        final FlowDomain flowDomain = FlowMapper.fromEntityToDomain(flowEntity);
        final FlowEntity newFlowEntity = FlowMapper.fromDomainToEntity(flowDomain);

        // should
        AssertionsForClassTypes.assertThat(newFlowEntity).as("Flow entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newFlowEntity.getId()).as("Flow id should remain the same").isEqualTo(flowEntity.getId());
        AssertionsForClassTypes.assertThat(newFlowEntity.getName()).as("Flow name should remain the same").isEqualTo(flowEntity.getName());
        AssertionsForClassTypes.assertThat(newFlowEntity.getIsActiveVersion()).as("Flow active version flag should remain the same").isEqualTo(flowEntity.getIsActiveVersion());
        AssertionsForClassTypes.assertThat(newFlowEntity.getPsoType()).as("Flow id should remain the same").isEqualTo(flowEntity.getPsoType());
        AssertionsForClassTypes.assertThat(newFlowEntity.getIsActive()).as("Flow active status should remain the same").isEqualTo(flowEntity.getIsActive());
        AssertionsForClassTypes.assertThat(newFlowEntity.getType()).as("Flow type should remain the same").isEqualTo(flowEntity.getType());
        AssertionsForClassTypes.assertThat(newFlowEntity.getExecutionStatus()).as("Flow execution status should remain the same").isEqualTo(flowEntity.getExecutionStatus());
        AssertionsForClassTypes.assertThat(newFlowEntity.getExecutionStatusDate()).as("Flow execution status date should remain the same").isEqualTo(flowEntity.getExecutionStatusDate());
        AssertionsForClassTypes.assertThat(newFlowEntity.getExecutionType()).as("Flow execution type should remain the same").isEqualTo(flowEntity.getExecutionType());
        AssertionsForClassTypes.assertThat(newFlowEntity.getMappingStatus()).as("Flow mapping status should remain the same").isEqualTo(flowEntity.getMappingStatus());
        AssertionsForClassTypes.assertThat(newFlowEntity.getClient()).as("Flow client should remain null until completion with proxy bag").isNull();
        AssertionsForClassTypes.assertThat(newFlowEntity.getConnector()).as("Flow connector should remain null until completion with proxy bag").isNull();
        AssertionsForClassTypes.assertThat(newFlowEntity.getModule()).as("Flow module should remain null until completion with proxy bag").isNull();
        AssertionsForInterfaceTypes.assertThat(newFlowEntity.getPopulations()).as("Flow target populations should remain empty until completion with proxy bag").isEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test mapping between flow DTOs and domain instances")
    void fromDomainToDTOBackToDomain_shouldRemainTheSame() {
        //given
        final ClientDomain clientDomain = new ClientDomain();
        clientDomain.setId(CLIENT_ID);
        clientDomain.setName("orchestrate");
        final ModuleDomain moduleDomain = new ModuleDomain();
        moduleDomain.setId(8_765L);
        final FlowDomain flowDomain = new FlowDomain();
        flowDomain.setId(1L);
        flowDomain.setName("Legal entity");
        flowDomain.setFlowVersion("V1.1");
        flowDomain.setPsoType("Flow id");
        flowDomain.setIsActive(true);
        flowDomain.setType(FlowDirectionEnum.IN);
        flowDomain.setExecutionStatus(ExecutionStatusEnum.RUNNING);
        flowDomain.setExecutionStatusDate(OffsetDateTime.now());
        flowDomain.setExecutionType(ExecutionTypeEnum.SCHEDULED);
        flowDomain.setMappingStatus(MappingStatusEnum.CONFIRMED);
        flowDomain.setClientId(clientDomain.getId());
        flowDomain.setConnectorId(1L);
        flowDomain.setModuleId(moduleDomain.getId());
        flowDomain.setTargetPopulations(Set.of("Groupe paris", "Groupe Montpellier"));

        // then
        final FlowDTO flowDTO = FlowMapper.fromDomainToDto(flowDomain);
        final FlowDomain newFlowDomain = FlowMapper.fromDtoToDomain(flowDTO);

        // should
        AssertionsForClassTypes.assertThat(newFlowDomain).as("Flow domain instance should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newFlowDomain.getId()).as("Flow id should remain the same").isEqualTo(flowDomain.getId());
        AssertionsForClassTypes.assertThat(newFlowDomain.getName()).as("Flow name should remain the same").isEqualTo(flowDomain.getName());
        AssertionsForClassTypes.assertThat(newFlowDomain.getType()).as("Flow type should remain the same").isEqualTo(flowDomain.getType());
        AssertionsForClassTypes.assertThat(newFlowDomain.getType()).as("Flow type should remain the same").isEqualTo(flowDomain.getType());
        AssertionsForClassTypes.assertThat(newFlowDomain.getExecutionStatus()).as("Flow execution status should remain the same").isEqualTo(flowDomain.getExecutionStatus());
        AssertionsForClassTypes.assertThat(newFlowDomain.getExecutionStatusDate()).as("Flow execution status date should remain the same").isEqualTo(flowDomain.getExecutionStatusDate());
        AssertionsForClassTypes.assertThat(newFlowDomain.getExecutionType()).as("Flow execution type should remain the same").isEqualTo(flowDomain.getExecutionType());
        AssertionsForClassTypes.assertThat(newFlowDomain.getMappingStatus()).as("Flow mapping status should remain the same").isEqualTo(flowDomain.getMappingStatus());
        AssertionsForInterfaceTypes.assertThat(newFlowDomain.getTargetPopulations()).as("Flow target populations should remain the same").hasSize(2);
        AssertionsForClassTypes.assertThat(newFlowDomain.getConnectorId()).as("Flow connector id should remain the same").isEqualTo(flowDomain.getConnectorId());
        AssertionsForClassTypes.assertThat(newFlowDomain.getClientId()).as("Flow client id should remain null until completion with proxy bag").isEqualTo("33333330-3336-3331-2d33-3633312d3336");
        AssertionsForClassTypes.assertThat(newFlowDomain.getModuleId()).as("Flow module id should remain null until completion with proxy bag").isEqualTo(8_765L);
    }
}