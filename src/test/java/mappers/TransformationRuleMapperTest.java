package mappers;

// Local imports

import com.peoplespheres.domain.TransformationRuleDomain;
import com.peoplespheres.dto.TransformationRuleDTO;
import com.peoplespheres.entites.AuditEmbedded;
import com.peoplespheres.entites.TransformationRuleEntity;
import com.peoplespheres.enums.RuleTypeEnum;
import com.peoplespheres.mappers.TransformationRuleMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("Unit test for the behaviour of the Transformation Rule mapper")
class TransformationRuleMapperTest {
    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between transformation rule entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final TransformationRuleEntity transformationRuleEntity = new TransformationRuleEntity();
        transformationRuleEntity.setId(1);
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        transformationRuleEntity.setAuditEmbedded(auditEmbedded);
        transformationRuleEntity.setIsInternal(false);
        transformationRuleEntity.setFunction(RuleTypeEnum.FORMAT);
        transformationRuleEntity.setName("Format");
        transformationRuleEntity.setParamNumberMin(1);
        transformationRuleEntity.setParamNumberMax(1);

        // then
        final TransformationRuleDomain transformationRuleDomain = TransformationRuleMapper.fromEntityToDomain(transformationRuleEntity);
        final TransformationRuleEntity newTransformationEntity = TransformationRuleMapper.fromDomainToEntity(transformationRuleDomain);

        // should
        AssertionsForClassTypes.assertThat(newTransformationEntity).as("Transformation rule entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newTransformationEntity.getId()).as("Transformation rule id should remain the same").isEqualTo(transformationRuleEntity.getId());
        AssertionsForClassTypes.assertThat(newTransformationEntity.getIsInternal()).as("Transformation rule internal flag should remain the same").isEqualTo(transformationRuleEntity.getIsInternal());
        AssertionsForClassTypes.assertThat(newTransformationEntity.getFunction()).as("Transformation rule function should remain the same").isEqualTo(transformationRuleEntity.getFunction());
        AssertionsForClassTypes.assertThat(newTransformationEntity.getName()).as("Transformation rule name should remain the same").isEqualTo(transformationRuleEntity.getName());
        AssertionsForClassTypes.assertThat(newTransformationEntity.getParamNumberMin()).as("Transformation rule minimum of parameters should remain the same").isEqualTo(transformationRuleEntity.getParamNumberMin());
        AssertionsForClassTypes.assertThat(newTransformationEntity.getParamNumberMax()).as("Transformation rule maximum of parameters should remain the same").isEqualTo(transformationRuleEntity.getParamNumberMax());
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test mapping between transformation rule entities and domain instances")
    void fromDomainToDTOBackToDomain_shouldRemainTheSame() {
        //given
        final TransformationRuleDomain transformationRuleDomain = new TransformationRuleDomain();
        transformationRuleDomain.setId(1);
        transformationRuleDomain.setCreatedAt(Instant.now());
        transformationRuleDomain.setIsInternal(false);
        transformationRuleDomain.setFunction(RuleTypeEnum.FORMAT);
        transformationRuleDomain.setName("Format");
        transformationRuleDomain.setParamNumberMin(1);
        transformationRuleDomain.setParamNumberMax(1);

        // then
        final TransformationRuleDTO transformationRuleDTO = TransformationRuleMapper.fromDomainToDTO(transformationRuleDomain);
        final TransformationRuleDomain newTransformationDomain = TransformationRuleMapper.fromDtoToDomain(transformationRuleDTO);

        // should
        AssertionsForClassTypes.assertThat(newTransformationDomain).as("Transformation rule domain instance should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newTransformationDomain.getId()).as("Transformation rule id should remain the same").isEqualTo(transformationRuleDomain.getId());
        AssertionsForClassTypes.assertThat(newTransformationDomain.getIsInternal()).as("Transformation rule internal flag should remain the same").isEqualTo(transformationRuleDomain.getIsInternal());
        AssertionsForClassTypes.assertThat(newTransformationDomain.getFunction()).as("Transformation rule function should remain the same").isEqualTo(transformationRuleDomain.getFunction());
        AssertionsForClassTypes.assertThat(newTransformationDomain.getName()).as("Transformation rule name should remain the same").isEqualTo(transformationRuleDomain.getName());
        AssertionsForClassTypes.assertThat(newTransformationDomain.getParamNumberMin()).as("Transformation rule minimum of parameters should remain the same").isEqualTo(transformationRuleDomain.getParamNumberMin());
        AssertionsForClassTypes.assertThat(newTransformationDomain.getParamNumberMax()).as("Transformation rule maximum of parameters should remain the same").isEqualTo(transformationRuleDomain.getParamNumberMax());
    }
}