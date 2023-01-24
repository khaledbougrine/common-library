package dto;

import com.peoplespheres.dto.ClientFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

 class ClientFullDTOTest {


    @Test
    @DisplayName("Test the getter and setter methods of the full client dto class")
     void getterAndSetterCorrectness()  {
        new BeanTester().testBean(ClientFullDTO.class);
    }


}