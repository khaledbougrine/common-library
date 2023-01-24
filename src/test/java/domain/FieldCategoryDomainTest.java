package domain;

import com.peoplespheres.domain.FieldCategoryDomain;
import com.peoplespheres.dto.ClientFullDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

@DisplayName("Test the FieldCategoryDomain class")
 class FieldCategoryDomainTest {

    @Test
    @DisplayName("Test the getter and setter methods of the FieldCategoryDomainclass")
    void getterAndSetterCorrectness()  {
        new BeanTester().testBean(FieldCategoryDomain.class);
    }

}
