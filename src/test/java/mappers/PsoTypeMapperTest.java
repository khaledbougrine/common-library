package mappers;

import com.peoplespheres.domain.PsoTypeDomain;
import com.peoplespheres.dto.PsoTypeDTO;
import com.peoplespheres.entites.FieldCategoryEntity;
import com.peoplespheres.entites.FieldEntity;
import com.peoplespheres.entites.PsoTypeEntity;
import com.peoplespheres.mappers.PsoTypeMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the psoType mapper")
 class PsoTypeMapperTest {
    private PsoTypeEntity psoTypeEntity;

    private FieldEntity fieldEntity;

    private FieldCategoryEntity fieldCategoryEntity ;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        psoTypeEntity = helperClass.getEntity_Helper("PsoTypes/pso_type.json", PsoTypeEntity.class);
        fieldEntity = helperClass.getEntity_Helper("Fields/field_category_entity.json", FieldEntity.class);
        fieldCategoryEntity = helperClass.getEntity_Helper("FieldCategories/fieldCategories_item.json", FieldCategoryEntity.class);
        fieldEntity.setCategory(fieldCategoryEntity);
        psoTypeEntity.setFields(Set.of(fieldEntity));
    }

    @Test
    @DisplayName("Test the mapping for a psoType instance ")
    void psoType_instance_Test() {

        PsoTypeDomain psoTypeDomain =  PsoTypeMapper.fromEntityToDomain(psoTypeEntity);
        PsoTypeDTO psoTypeDTO =  PsoTypeMapper.fromDomainToDTO(psoTypeDomain);

        // Test mapping between entity and domain instances
        assertThat(psoTypeEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","alias","is_active","name","clientId","created_at",
                        "updated_at","deleted_at","version").
                isEqualTo(psoTypeDomain);

        // Test mapping between Domain and Dto instances
        assertThat(psoTypeDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","alias","is_active","name","clientId",
                        "created_at","updated_at","deleted_at","version").
                isEqualTo(psoTypeDTO);
    }

    }
