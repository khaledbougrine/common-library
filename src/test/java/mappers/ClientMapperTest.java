package mappers;

import com.peoplespheres.domain.ClientDomain;
import com.peoplespheres.dto.ClientDTO;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.ClientSettingEntity;
import com.peoplespheres.entites.ConnectorEntity;
import com.peoplespheres.mappers.ClientMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the client mapper")
class ClientMapperTest {

    private ClientEntity clientEntity;

    private Collection<ConnectorEntity> connectorEntities;

    private Collection<ClientSettingEntity> clientSettingEntities;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        clientEntity = helperClass.getEntity_Helper("Entities/clientEntity.json", ClientEntity.class);
        connectorEntities = helperClass.getEntities_Helper("Connectors/connector.json", ConnectorEntity.class);
        clientSettingEntities = helperClass.getEntities_Helper("Entities/clientSetting_list.json", ClientSettingEntity.class);
        clientEntity.setClientSettings(new HashSet<>(clientSettingEntities));
        clientEntity.setConnectors(new HashSet<>(connectorEntities));
    }

    @Test
    @DisplayName("Test the mapping for a client instance ")
    void client_instance_Test() {
        ClientDomain clientDomain=ClientMapper.fromEntityToDomain(clientEntity);
        ClientDTO clientDto=ClientMapper.fromDomainToDto(clientDomain);


        // Test mapping between entity and domain instances
        assertThat(clientEntity).usingRecursiveComparison().comparingOnlyFields("id","name","alias","description","isEnabled").
        isEqualTo(clientDomain);

        // Test mapping between domain and dto instances
        assertThat(clientDto).usingRecursiveComparison().comparingOnlyFields("id","name","alias","description","isEnabled").
                isEqualTo(clientDomain);
    }
}
