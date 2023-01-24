package mappers;

import com.peoplespheres.domain.FieldDomain;
import com.peoplespheres.entites.FieldCategoryEntity;
import com.peoplespheres.entites.FieldEntity;
import com.peoplespheres.entites.PsoTypeEntity;
import com.peoplespheres.mappers.FieldMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the field mapper")
 class FieldMapperTest {
    private FieldEntity fieldEntity;
    private PsoTypeEntity psoTypeEntity;

    private FieldCategoryEntity category;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        fieldEntity = helperClass.getEntity_Helper("Entities/fieldEntity.json", FieldEntity.class);
        psoTypeEntity = helperClass.getEntity_Helper("PsoTypes/pso_type.json", PsoTypeEntity.class);
        category = helperClass.getEntity_Helper("FieldCategories/fieldCategories_item.json", FieldCategoryEntity.class);
        fieldEntity.setPsoType(psoTypeEntity);
        fieldEntity.setCategory(category);
    }

    @Test
    @DisplayName("Test the mapping for a field instance ")
    void from_EntityToDomain_And_DomainToDto_Test() {
        // Test mapping between entity and domain instances
        FieldDomain fieldDomain =  FieldMapper.fromEntityToDomain(fieldEntity);
        assertThat(fieldEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","alias","name","pso_type_id","category_id","created_at","updated_at","" +
                                "deleted_at","version","is_active").
                isEqualTo(fieldDomain);
        // Test mapping between Domain and Dto instances
        assertThat(fieldDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","alias","name","pso_type_id","category_id","created_at","updated_at","" +
                                "deleted_at","version","is_active").
                isEqualTo(FieldMapper.fromDomainToDTO(fieldDomain));
    }
}
