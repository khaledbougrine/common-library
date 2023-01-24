package dto;

import com.peoplespheres.dto.TemplateImportDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
@DisplayName("Test the TemplateImportDTO class")
 class TemplateImportDTOTest {

    @Test
    @DisplayName("Test the getter and setter methods of the TemplateImportDTO class")
    void getterAndSetterCorrectness()  {
        new BeanTester().testBean(TemplateImportDTO.class);
    }
}
