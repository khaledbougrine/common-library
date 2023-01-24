package mappers;

// Local imports

import com.peoplespheres.domain.MatchingTableDomain;
import com.peoplespheres.entites.AuditEmbedded;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.FlowEntity;
import com.peoplespheres.entites.MatchingTableEntity;
import com.peoplespheres.mappers.MatchingTableMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.Instant;
@DisplayName("Unit test for the behaviour of the matching table mapper")
class MatchingTableMapperTest {
    /** Unique identifier of the client orchestrate used in this test */
    private static final String CLIENT_ID = "33333330-3336-3331-2d33-3633312d3336"; // Orchestrate

    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between matching table entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(1L);
        flowEntity.setName("Flow test");
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setName("orchestrate");
        final MatchingTableEntity matchingTableEntity = new MatchingTableEntity();
        matchingTableEntity.setId(1L);
        matchingTableEntity.setFieldOptionName("usr_payroll_code_excep");
        matchingTableEntity.setFieldValue("Code prime");
        matchingTableEntity.setIsActive(true);
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        matchingTableEntity.setAuditEmbedded(auditEmbedded);

        // then
        final MatchingTableDomain matchingTableDomain = MatchingTableMapper.fromEntityToDomain(matchingTableEntity);
        final MatchingTableEntity newMatchingTableEntity = MatchingTableMapper.fromDomainToEntity(matchingTableDomain);

        // should
        AssertionsForClassTypes.assertThat(newMatchingTableEntity).as("Matching table entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newMatchingTableEntity.getId()).as("Matching table id should remain the same").isEqualTo(matchingTableEntity.getId());
        AssertionsForClassTypes.assertThat(newMatchingTableEntity.getFieldValue()).as("Matching table field value should remain the same").isEqualTo(matchingTableEntity.getFieldValue());
        AssertionsForClassTypes.assertThat(newMatchingTableEntity.getFieldOptionName()).as("Matching table field option name should remain the same").isEqualTo(matchingTableEntity.getFieldOptionName());
        AssertionsForClassTypes.assertThat(newMatchingTableEntity.getIsActive()).as("Matching table active status should remain the same").isEqualTo(matchingTableEntity.getIsActive());
    }
}