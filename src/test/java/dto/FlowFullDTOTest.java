package dto;


import com.peoplespheres.dto.FlowFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;


class FlowFullDTOTest {


    @Test
    @DisplayName("Test the getter and setter methods of the flow full dto class")
    void getterAndSetterCorrectness() {
        new BeanTester().testBean(FlowFullDTO.class);
    }


}
