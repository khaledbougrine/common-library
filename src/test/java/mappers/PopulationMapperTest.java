package mappers;

// Local imports

import com.peoplespheres.domain.PopulationDomain;
import com.peoplespheres.dto.PopulationDTO;
import com.peoplespheres.entites.AuditEmbedded;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.PopulationEntity;
import com.peoplespheres.mappers.PopulationMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("Unit test for the behaviour of the population mapper")
class PopulationMapperTest {
    /** Unique identifier of the client orchestrate used in this test */
    private static final String CLIENT_ID = "33333330-3336-3331-2d33-3633312d3336"; // Orchestrate

    @Test
    @Order(1)
    @DisplayName("1 - Test mapping between population entities and domain instances")
    void fromEntityToDomainBackToEntity_shouldRemainTheSame() {
        //given
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setName("orchestrate");
        final PopulationEntity populationEntity = new PopulationEntity();
        populationEntity.setId(1L);
        populationEntity.setAlias("usr_group_ps_paris");
        populationEntity.setName("Groupe Paris");
        final AuditEmbedded auditEmbedded = new AuditEmbedded();
        auditEmbedded.setCreatedAt(Instant.now());
        populationEntity.setAuditEmbedded(auditEmbedded);
        populationEntity.setClient(clientEntity);

        // then
        final PopulationDomain populationDomain = PopulationMapper.fromEntityToDomain(populationEntity);
        final PopulationEntity newPopulationEntity = PopulationMapper.fromDomainToEntity(populationDomain);

        // should
        AssertionsForClassTypes.assertThat(newPopulationEntity).as("Population entity should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newPopulationEntity.getId()).as("Population id should remain the same").isEqualTo(populationDomain.getId());
        AssertionsForClassTypes.assertThat(newPopulationEntity.getName()).as("Population name should remain the same").isEqualTo(populationDomain.getName());
        AssertionsForClassTypes.assertThat(newPopulationEntity.getAlias()).as("Population alias should remain the same").isEqualTo(populationDomain.getAlias());
        //AssertionsForClassTypes.assertThat(newPopulationEntity.getAuditEmbedded().getCreatedAt()).as("Population creation date should remain the same").isEqualTo(populationDomain.getCreatedAt());
        AssertionsForClassTypes.assertThat(newPopulationEntity.getClient()).as("Population client should remain null until completion with proxy bag").isNull();
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test mapping between population entities and domain instances")
    void fromDomainToDTOBackToDomain_shouldRemainTheSame() {
        //given
        final PopulationDomain populationDomain = new PopulationDomain();
        populationDomain.setId(1L);
        populationDomain.setAlias("usr_group_ps_paris");
        populationDomain.setName("Groupe Paris");
        populationDomain.setCreatedAt(Instant.now());
        populationDomain.setMemberCount(35);

        // then
        final PopulationDTO populationDTO = PopulationMapper.fromDomainToDTO(populationDomain);
        final PopulationDomain newPopulationDomain = PopulationMapper.fromDtoToDomain(populationDTO);

        // should
        AssertionsForClassTypes.assertThat(newPopulationDomain).as("Population domain instance should not be null").isNotNull();
        AssertionsForClassTypes.assertThat(newPopulationDomain.getId()).as("Population id should remain the same").isEqualTo(populationDomain.getId());
        AssertionsForClassTypes.assertThat(newPopulationDomain.getId()).as("Population id should remain the same").isEqualTo(populationDomain.getId());
        AssertionsForClassTypes.assertThat(newPopulationDomain.getName()).as("Population name should remain the same").isEqualTo(populationDomain.getName());
        AssertionsForClassTypes.assertThat(newPopulationDomain.getAlias()).as("Population alias should remain the same").isEqualTo(populationDomain.getAlias());
        AssertionsForClassTypes.assertThat(newPopulationDomain.getClient()).as("Population client id should remain null until the completion with the proxy bag").isNull();
    }
}