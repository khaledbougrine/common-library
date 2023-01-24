package exception;

import com.peoplespheres.domain.ScheduleWithSeveralDaysDomain;
import com.peoplespheres.exception.ScheduleNotValidException;
import com.peoplespheres.mappers.ScheduleWithSeveralDaysMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Schedule Not Valid Exception Test")
class ScheduleNotValidExceptionTest {

    @Autowired
    private AdapterHelper helperClass;

    private ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain;

    @BeforeEach
    private void setUp() {
        scheduleWithSeveralDaysDomain  = helperClass.getEntity_Helper("Entities/notValidSchedules.json", ScheduleWithSeveralDaysDomain.class);
    }


    @Test
    @DisplayName("Schedule Not Valid Exception Test")
    void ScheduleNotValidException_Test() {
        assertThatExceptionOfType(ScheduleNotValidException.class)
                .isThrownBy(() -> {
                    ScheduleWithSeveralDaysMapper.fromDomainToEntities(scheduleWithSeveralDaysDomain);
                });
    }
}
