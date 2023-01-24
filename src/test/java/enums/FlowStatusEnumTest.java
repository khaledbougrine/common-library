package enums;

// JUnit imports
import com.peoplespheres.enums.FlowStatusEnum;
import org.junit.jupiter.api.*;

// AssertJ imports
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test of enumeration FlowStatusEnumTest")
class FlowStatusEnumTest {
    @Test
    @Order(1)
    @DisplayName("1 - Checking that value of enumeration FlowStatusEnum for null is null")
    void of() {
        assertThat(FlowStatusEnum.of(null)).as("Flow status enumeration for null should be null !!").isNull();
    }
}