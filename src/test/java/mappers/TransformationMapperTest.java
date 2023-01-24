package mappers;

import com.peoplespheres.domain.TransformationDomain;
import com.peoplespheres.dto.TransformationDTO;
import com.peoplespheres.dto.TransformationFullDTO;
import com.peoplespheres.entites.ModuleEntity;
import com.peoplespheres.entites.TransformationEntity;
import com.peoplespheres.entites.TransformationRuleEntity;
import com.peoplespheres.mappers.TransformationMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the transformation mapper")
 class TransformationMapperTest {
    private TransformationEntity transformationEntity;
    private TransformationDomain transformationDomain;
    private TransformationDTO transformationDTO;

    private TransformationRuleEntity transformationRule;

    @Autowired
    private AdapterHelper helperClass;
    private TransformationFullDTO transformationFullDTO;
    private ModuleEntity moduleEntity;


    @BeforeEach
    private void setUp() {
        transformationEntity = helperClass.getEntity_Helper("Entities/transformationEntity_item.json", TransformationEntity.class);
        transformationRule  = helperClass.getEntity_Helper("TransformationRules/transformationRull_item.json", TransformationRuleEntity.class);
        moduleEntity = helperClass.getEntity_Helper("Entities/moduleEntity_Item.json", ModuleEntity.class);
        transformationEntity.setTransformationRule(transformationRule);
        transformationEntity.setModule(moduleEntity);
        transformationDomain = TransformationMapper.fromEntityToDomain(transformationEntity);
        transformationDTO =TransformationMapper.fromDomainToDto(transformationDomain);
        transformationFullDTO =TransformationMapper.fromDomainToFullDto(transformationDomain);
    }

    @Test
    @DisplayName("Test mapping between entities and domain instances")
    void fromEntityToDomainTest() {
        assertThat(transformationEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromEntityToDomain(transformationEntity));
    }

    @Test
    @DisplayName("Test mapping between Domain and dto instances")
    void fromDomainToDto_And_FullDtoTest_backToDomain() {
        assertThat(transformationDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromDomainToDto(transformationDomain));

        assertThat(transformationDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromDomainToFullDto(transformationDomain));

        assertThat(transformationDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromFullDtoToDomain(transformationFullDTO));
        transformationDTO.setTransformationRule(null);
        assertThat(transformationDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromDtoToDomain(transformationDTO));
    }


    @Test
    @DisplayName("Test mapping between domain and entity instances")
    void fromDomainToEntityTest() {
        assertThat(transformationDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","direction","flow_type","moduleId","name","parameters",
                        "position","sourceId","targetId","transformationRuleId","created_at","updated_at","deleted_at","version").
                isEqualTo(TransformationMapper.fromDomainToEntity(transformationDomain));
    }
}
