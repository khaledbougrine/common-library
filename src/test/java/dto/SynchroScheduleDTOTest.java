package dto;

import com.peoplespheres.dto.SynchroScheduleDTO;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import configuration.WebMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test the SynchroScheduleDTO class")
@SpringBootTest(classes = WebMvcConfig.class)
 class SynchroScheduleDTOTest {

    private SynchroScheduleDTO synchroScheduleDTO;

    @Autowired
    private AdapterHelper helperClass;

    @BeforeEach
    private void setUp() {
        synchroScheduleDTO = helperClass.getEntity_Helper("Schedules/synchroSchedule.json", SynchroScheduleDTO.class);
    }

    @Test
    @DisplayName("Test the getter and setter methods of the SynchroScheduleDTO class")
    void getterAndSetterCorrectness()  {
        assertThat(synchroScheduleDTO.getStartDate()).isEqualTo("2022-09-28T23:33:03Z");
        assertThat(synchroScheduleDTO.getEndDate()).isEqualTo("2022-09-28T23:33:03Z");
        assertThat(synchroScheduleDTO.getIsMaintenance()).isFalse();
        assertThat(synchroScheduleDTO.getFrequency()).isEqualTo(FlowScheduleFrequencyEnum.MONTHLY);
        assertThat(synchroScheduleDTO.getExecutionTime()).isEqualTo("18:00:00");
        assertThat(synchroScheduleDTO.getFlowId()).isEqualTo(2);
        assertThat(synchroScheduleDTO.getClientId()).isEqualTo("33333330-3336-3331-2d33-3633312d3336");
        assertThat(synchroScheduleDTO.getModuleName()).isEqualTo("moduleNameTest");
        assertThat(synchroScheduleDTO.getModuleId()).isEqualTo(123L);
        assertThat(synchroScheduleDTO.getClientName()).isEqualTo("testName");
        assertThat(synchroScheduleDTO.getFlowName()).isEqualTo("test");
        assertThat(synchroScheduleDTO.getFlowId()).isEqualTo(2L);
        assertThat(synchroScheduleDTO.getFlowStatus()).isEqualTo("testStatus");

    }
}
