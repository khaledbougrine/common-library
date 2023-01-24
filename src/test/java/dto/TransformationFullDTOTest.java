package dto;

import com.peoplespheres.dto.ConnectorFullDTO;
import com.peoplespheres.dto.TransformationFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

@DisplayName("Test the TransformationFullDTO class")
 class TransformationFullDTOTest {
    @Test
    @DisplayName("Test the getter and setter methods of the TransformationFullDTO class")
    void getterAndSetterCorrectness()  {
        new BeanTester().testBean(TransformationFullDTO.class);
    }
}
