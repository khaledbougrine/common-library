package dto;

import com.peoplespheres.dto.MappingFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

@DisplayName("Test the MappingFullDTO class")
 class MappingFullDTOTest {
    @Test
    @DisplayName("Test the getter and setter methods of the MappingFullDTO class")
    void getterAndSetterCorrectness()  {
        new BeanTester().testBean(MappingFullDTO.class);
    }
}
