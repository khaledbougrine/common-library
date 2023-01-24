package mappers;

import com.peoplespheres.domain.DataSourceDomain;
import com.peoplespheres.dto.DataSourceDTO;
import com.peoplespheres.dto.DataSourceFullDTO;
import com.peoplespheres.entites.DataSourceEntity;
import com.peoplespheres.entites.PsoTypeEntity;
import com.peoplespheres.mappers.DataSourceMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the data source mapper")
 class DataSourceMappingTest {

    private DataSourceEntity dataSourceEntity;
    private DataSourceDomain dataSourceDomain;
    private DataSourceDTO dataSourceDTO;

    private PsoTypeEntity psoTypeEntity;
    private DataSourceFullDTO dataSourceFullDTO;




    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        dataSourceEntity = helperClass.getEntity_Helper("Entities/datasourceEntity.json", DataSourceEntity.class);
        psoTypeEntity  = helperClass.getEntity_Helper("PsoTypes/pso_type.json", PsoTypeEntity.class);
        dataSourceEntity.setPsoType(psoTypeEntity);
        dataSourceEntity.setRelatedPsoType(psoTypeEntity);
        dataSourceDomain=DataSourceMapper.fromEntityToDomain(dataSourceEntity);
        dataSourceDTO=DataSourceMapper.fromDomainToDTO(dataSourceDomain);
        dataSourceFullDTO=DataSourceMapper.fromDomainToFullDTO(dataSourceDomain);


    }

    @Test
    @DisplayName("Test mapping between entities and domain instances")
    void fromEntityToDomainTest() {
        assertThat(dataSourceEntity).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromEntityToDomain(dataSourceEntity));
    }

    @Test
    @DisplayName("Test mapping between Domain and dto instances")
    void fromDomainToDtoTest() {
        assertThat(dataSourceDomain).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromDomainToDTO(dataSourceDomain));

    }
    @Test
    @DisplayName("Test mapping between Domain and dto instances")
    void fromDomainToFullDtoTest() {
        assertThat(dataSourceDomain).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromDomainToFullDTO(dataSourceDomain));
    }

    @Test
    @DisplayName("Test mapping between dto and domain instances")
    void fromDtoToDomainTest() {
        assertThat(dataSourceDTO).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromDtoToDomain(dataSourceDTO));
    }

    @Test
    @DisplayName("Test mapping between FullDto and domain instances")
    void fromFullDtoToDomainTest() {
        assertThat(dataSourceFullDTO).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromFullDtoToDomain(dataSourceFullDTO));
    }
    @Test
    @DisplayName("Test mapping between domain and entity instances")
    void fromDomainToEntityTest() {
        assertThat(dataSourceDomain).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(DataSourceMapper.fromDomainToEntity(dataSourceDomain));
    }
}
