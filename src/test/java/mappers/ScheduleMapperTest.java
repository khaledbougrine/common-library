package mappers;

// Local imports

import com.peoplespheres.domain.ScheduleDomain;
import com.peoplespheres.dto.ScheduleDTO;
import com.peoplespheres.dto.ScheduleFullDTO;
import com.peoplespheres.entites.AuditEmbedded;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.FlowEntity;
import com.peoplespheres.entites.ScheduleEntity;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import com.peoplespheres.mappers.ScheduleMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the schedule mapper")
class ScheduleMapperTest {
    /** Unique identifier of the client orchestrate used in this test */
    private static final String CLIENT_ID = "33333330-3336-3331-2d33-3633312d3336"; // Orchestrate

    @Autowired
    private AdapterHelper helperClass;

    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between schedule entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setName("orchestrate");
        final FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(1L);
        flowEntity.setName("Legal entity");
        final ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(1L);
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        scheduleEntity.setAuditEmbedded(auditEmbedded);
        scheduleEntity.setClient(clientEntity);
        //scheduleEntity.setFlow(flowEntity);
        scheduleEntity.setSelectDay("FRIDAY");
        scheduleEntity.setExecutionTime(LocalTime.MIDNIGHT);
        scheduleEntity.setIsMaintenance(false);

        // then
        final ScheduleDomain scheduleDomain = ScheduleMapper.fromEntityToDomain(scheduleEntity);
        final ScheduleEntity newScheduleEntity = ScheduleMapper.fromDomainToEntity(scheduleDomain);

        // should
        AssertionsForClassTypes.assertThat(newScheduleEntity).as("Schedule entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newScheduleEntity.getId()).as("Schedule id should remain the same").isEqualTo(scheduleEntity.getId());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getStartDate()).as("Schedule start date should remain the same").isEqualTo(scheduleEntity.getStartDate());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getEndDate()).as("Schedule end date should remain the same").isEqualTo(scheduleEntity.getEndDate());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getFrequency()).as("Schedule frequency should remain the same").isEqualTo(scheduleEntity.getFrequency());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getExecutionTime()).as("Schedule execution time should remain the same").isEqualTo(scheduleEntity.getExecutionTime());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getSelectDay()).as("Schedule selected day should remain the same").isEqualTo(scheduleEntity.getSelectDay());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getIsMaintenance()).as("Schedule maintenance mode should remain the same").isEqualTo(scheduleEntity.getIsMaintenance());
        AssertionsForClassTypes.assertThat(newScheduleEntity.getClient()).as("Schedule client should remain null until completion with proxy bag").isNull();
        //AssertionsForClassTypes.assertThat(newScheduleEntity.getFlow()).as("Schedule flow should remain null until completion with proxy bag").isNull();
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test mapping between schedule DTOs and domain instances")
    void fromDomainToDTOBackToDomain_shouldRemainTheSame() {
        //given
        final ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setId(1L);
        scheduleDomain.setStartDate(Instant.now());
        scheduleDomain.setEndDate(Instant.now());
        scheduleDomain.setFrequency(FlowScheduleFrequencyEnum.MONTHLY);
        scheduleDomain.setExecutionTime(LocalTime.MIDNIGHT);
        scheduleDomain.setSelectDay("FRIDAY");
        scheduleDomain.setIsMaintenance(false);
        scheduleDomain.setClientId(CLIENT_ID);
        scheduleDomain.setFlowId(1L);

        // then
        final ScheduleDTO scheduleDTO = ScheduleMapper.fromDomainToDTO(scheduleDomain);
        final ScheduleDomain newScheduleDomain = ScheduleMapper.fromDTOToDomain(scheduleDTO);

        // should
        AssertionsForClassTypes.assertThat(newScheduleDomain).as("Schedule domain instance should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newScheduleDomain.getId()).as("Schedule id should remain the same").isEqualTo(scheduleDomain.getId());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getStartDate()).as("Schedule start date should remain the same").isEqualTo(scheduleDomain.getStartDate());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getEndDate()).as("Schedule end date should remain the same").isEqualTo(scheduleDomain.getEndDate());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getFrequency()).as("Schedule frequency should remain the same").isEqualTo(scheduleDomain.getFrequency());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getExecutionTime()).as("Schedule execution time should remain the same").isEqualTo(scheduleDomain.getExecutionTime());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getSelectDay()).as("Schedule selected day should remain the same").isEqualTo(scheduleDomain.getSelectDay());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getIsMaintenance()).as("Schedule maintenance mode should remain the same").isEqualTo(scheduleDomain.getIsMaintenance());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getClientId()).as("Schedule client id should remain the same").isEqualTo(scheduleDomain.getClientId());
        AssertionsForClassTypes.assertThat(newScheduleDomain.getFlowId()).as("Schedule flow id should remain the same").isEqualTo(scheduleDomain.getFlowId());
    }

    @Test
    @Order(3)
    @DisplayName("3 - Test mapping between schedule FullDTOs and domain instances")
    void fromDomainToFullDTOBackToDomain_shouldRemainTheSame() {
        final ScheduleEntity scheduleEntity=helperClass.getEntity_Helper("Entities/scheduleEntity.json",ScheduleEntity.class);
        final ScheduleDomain scheduleDomain = ScheduleMapper.fromEntityToDomain(scheduleEntity);
        final ScheduleFullDTO scheduleFullDTO = ScheduleMapper.fromDomainToFullDTO(scheduleDomain);

        // Test mapping between domain and fullDto instances
        assertThat(scheduleDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","startDate","endDate","frequency",
                        "isMaintenance","selectDay","executionTime").
                isEqualTo(scheduleFullDTO);

        // Test mapping between fullDto and Domain instances
        assertThat(scheduleDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","startDate","endDate","frequency",
                        "isMaintenance","selectDay","executionTime").
                isEqualTo(ScheduleMapper.fromFullDtoToDomain(scheduleFullDTO));

    }
}