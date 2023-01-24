package mappers;


import com.peoplespheres.domain.ClientSettingDomain;
import com.peoplespheres.entites.ClientSettingEntity;
import com.peoplespheres.mappers.ClientSettingMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the client Setting mapper")
class ClientSettingMapperTest {

    private ClientSettingEntity clientSettingEntity;


    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        clientSettingEntity = helperClass.getEntity_Helper("Entities/clientSettingEntity.json", ClientSettingEntity.class);
    }

    @Test
    @DisplayName("Test mapping between  entities and domain instances")
    void fromEntityToDomainTest() {
        ClientSettingDomain clientSettingDomain = ClientSettingMapper.fromEntityToDomain(clientSettingEntity);
        assertThat(clientSettingDomain.getEnv()).isEqualTo(clientSettingEntity.getEnv());
        assertThat(clientSettingDomain.getId()).isEqualTo(clientSettingEntity.getId());
        assertThat(clientSettingDomain.getHostname()).isEqualTo(clientSettingEntity.getHostname());
    }


}
