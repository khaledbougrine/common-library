package mappers;

import com.peoplespheres.domain.ModuleDomain;
import com.peoplespheres.dto.ModuleDTO;
import com.peoplespheres.entites.ModuleEntity;
import com.peoplespheres.mappers.ModuleMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the module mapper")
 class ModuleMapperTest {

    private ModuleEntity moduleEntity;

    @Autowired
    private AdapterHelper helperClass;

    @BeforeEach
    private void setUp() {
        moduleEntity = helperClass.getEntity_Helper("Entities/moduleEntity_Item.json", ModuleEntity.class);
    }

    @Test
    @DisplayName("Test the mapping for a module instance ")
    void module_instance_Test() {

        ModuleDomain moduleDomain =  ModuleMapper.fromEntityToDomain(moduleEntity);
        ModuleDTO moduleDTO =  ModuleMapper.fromDomainToDTO(moduleDomain);

        // Test mapping between entity and domain instances
        assertThat(moduleEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","name","alias","partnerName","isActive").
                isEqualTo(moduleDomain);

        // Test mapping between Domain and Dto instances
        assertThat(moduleDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","name","alias","partnerName","isActive").
                isEqualTo(moduleDTO);

        // Test mapping between dto  and domain instances
        assertThat(moduleDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","name","alias","partnerName","isActive").
                isEqualTo(ModuleMapper.fromDTOToDomain(moduleDTO));

        // Test mapping between domain  and entity instances
        assertThat(moduleEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","name","alias","partnerName","isActive").
                isEqualTo(ModuleMapper.fromDomainToEntity(moduleDomain));



    }

}
