package dto;

import com.peoplespheres.dto.ConnectorFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

@DisplayName("Test the connectorFullDTO class")
 class ConnectorFullDTOTest {
    @Test
    @DisplayName("Test the getter and setter methods of the connectorFullDTO class")
    void getterAndSetterCorrectness()  {
        new BeanTester().testBean(ConnectorFullDTO.class);
    }

}
