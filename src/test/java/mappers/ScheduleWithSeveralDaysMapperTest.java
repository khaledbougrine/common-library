package mappers;

// Local imports

import com.peoplespheres.domain.ScheduleDomain;
import com.peoplespheres.domain.ScheduleWithSeveralDaysDomain;
import com.peoplespheres.dto.ScheduleWithSeveralDaysDTO;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.FlowEntity;
import com.peoplespheres.entites.ModuleEntity;
import com.peoplespheres.entites.ScheduleEntity;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import com.peoplespheres.mappers.ScheduleMapper;
import com.peoplespheres.mappers.ScheduleWithSeveralDaysMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import com.peoplespheres.exception.ScheduleNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the schedule with several days mapper")
class ScheduleWithSeveralDaysMapperTest {

    private List<ScheduleEntity> scheduleEntities;
    private ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain;

    private ScheduleWithSeveralDaysDTO scheduleWithSeveralDaysDTO;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        scheduleEntities = helperClass.getEntities_Helper("Entities/scheduleEntities.json", ScheduleEntity.class);
        scheduleWithSeveralDaysDomain = helperClass.getEntity_Helper("Entities/scheduleWithSeveralDaysDomain.json", ScheduleWithSeveralDaysDomain.class);
        scheduleWithSeveralDaysDTO  = helperClass.getEntity_Helper("Entities/scheduleWithSeveralDays.json", ScheduleWithSeveralDaysDTO.class);
    }


    @Test
    @Order(1)
    @DisplayName("1-Test mapping between entities and domain instances")
    void fromDomainToEntitiesTest() throws ScheduleNotValidException {
        var scheduleEntityResult = ScheduleWithSeveralDaysMapper.fromDomainToEntities(scheduleWithSeveralDaysDomain);
        assertThat(scheduleEntityResult.get(0).getId()).isEqualTo(scheduleEntities.get(0).getId());
        assertThat(scheduleEntityResult.get(0).getStartDate()).isEqualTo(scheduleEntities.get(0).getStartDate());
        assertThat(scheduleEntityResult.get(0).getEndDate()).isEqualTo(scheduleEntities.get(0).getEndDate());
        assertThat(scheduleEntityResult.get(0).getFrequency()).isEqualTo(scheduleEntities.get(0).getFrequency());
        assertThat(scheduleEntityResult.get(0).getExecutionTime()).isEqualTo(scheduleEntities.get(0).getExecutionTime());

    }

    @Test
    @Order(2)
    @DisplayName("2-Test mapping between schedule entities and domain instances")
    void fromEntitiesToDomain() throws  ScheduleNotValidException {
        ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomainResult = ScheduleWithSeveralDaysMapper.fromEntitiesToDomain(scheduleEntities);
        assertThat(scheduleWithSeveralDaysDomainResult.getSchedulesIds()).isEqualTo(scheduleWithSeveralDaysDomain.getSchedulesIds());
        assertThat(scheduleWithSeveralDaysDomainResult.getSelectDays()).isEqualTo(scheduleWithSeveralDaysDomain.getSelectDays());
        assertThat(scheduleWithSeveralDaysDomainResult.getStartDate()).isEqualTo(scheduleWithSeveralDaysDomain.getStartDate());
        assertThat(scheduleWithSeveralDaysDomainResult.getEndDate()).isEqualTo(scheduleWithSeveralDaysDomain.getEndDate());
        assertThat(scheduleWithSeveralDaysDomainResult.getFrequency()).isEqualTo(scheduleWithSeveralDaysDomain.getFrequency());
        assertThat(scheduleWithSeveralDaysDomainResult.getRepeated()).isEqualTo(scheduleWithSeveralDaysDomain.getRepeated());
        scheduleEntities.get(0).setFrequency(FlowScheduleFrequencyEnum.MONTHLY);
    }

    @Test
    @Order(3)
    @DisplayName("3-Test mapping between dto and domain instances")
    void fromDTOToDomainTest() {
        var scheduleWithSeveralDaysDomainResult = ScheduleWithSeveralDaysMapper.fromDTOToDomain(scheduleWithSeveralDaysDTO);
        assertThat(scheduleWithSeveralDaysDomainResult).usingRecursiveComparison().isEqualTo(scheduleWithSeveralDaysDomain);

    }

    @Test
    @Order(4)
    @DisplayName("4-Test mapping between dto and domain instances")
    void fromDomainToDTOTest() {
        var scheduleWithSeveralDaysDTOResult = ScheduleWithSeveralDaysMapper.fromDomainToDTO(scheduleWithSeveralDaysDomain);
        assertThat(scheduleWithSeveralDaysDTOResult).usingRecursiveComparison().isEqualTo(scheduleWithSeveralDaysDTO);

    }

    @Test
    @Order(6)
    @DisplayName("6-test the correspondence between a list of schedule domains and ScheduleWithSeveralDaysDomain")
    void scheduleDomainAggregationTest() throws  ScheduleNotValidException {
        List<ScheduleDomain> scheduleDomains = scheduleEntities.stream().map(scheduleEntity -> ScheduleMapper.fromEntityToDomain(scheduleEntity)).collect(Collectors.toList());
        ScheduleWithSeveralDaysDomain scheduleDomainAggregation = ScheduleWithSeveralDaysMapper.scheduleDomainAggregation(scheduleDomains);
        assertThat(scheduleDomainAggregation.getEndDate()).isEqualTo(scheduleDomains.get(0).getEndDate());
        assertThat(scheduleDomainAggregation.getStartDate()).isEqualTo(scheduleDomains.get(0).getStartDate());
        assertThat(scheduleDomainAggregation.getFrequency()).isEqualTo(scheduleDomains.get(0).getFrequency());
        assertThat(scheduleDomainAggregation.getRepeated()).isEqualTo(scheduleDomains.get(0).getRepeated());
        assertThat(scheduleDomainAggregation.getExecutionTime()).isEqualTo(scheduleDomains.get(0).getExecutionTime());
        var schedulesIds = scheduleDomainAggregation.getSchedulesIds().stream().toList();
        for (int i = 0; i < schedulesIds.size(); i++) {
            assertThat(schedulesIds.get(i)).isEqualTo(scheduleDomains.get(i).getId());
        }
    }
    @Test
    void fromSchedulesToSynchroSchedule_Test() throws ScheduleNotValidException {
        ClientEntity clientEntity = helperClass.getEntity_Helper("Entities/clientEntity.json",ClientEntity.class);
        FlowEntity flowEntity = helperClass.getEntity_Helper("Entities/flowEntity.json",FlowEntity.class);
        ModuleEntity moduleEntity = helperClass.getEntity_Helper("Entities/moduleEntity_Item.json",ModuleEntity.class);
        flowEntity.setModule(moduleEntity);
        var SynchroScheduleResult = ScheduleWithSeveralDaysMapper.fromSchedulesToSynchroSchedule(scheduleWithSeveralDaysDTO,flowEntity,clientEntity);
        assertThat(SynchroScheduleResult.get(0).getStartDate()).isEqualTo(scheduleWithSeveralDaysDTO.getStartDate());
        assertThat(SynchroScheduleResult.get(0).getEndDate()).isEqualTo(scheduleWithSeveralDaysDTO.getEndDate());
        assertThat(SynchroScheduleResult.get(0).getFrequency()).isEqualTo(scheduleWithSeveralDaysDTO.getFrequency());
        assertThat(SynchroScheduleResult.get(0).getExecutionTime()).isEqualTo(scheduleWithSeveralDaysDTO.getExecutionTime());
    }

}
