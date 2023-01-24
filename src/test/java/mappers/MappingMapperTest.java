package mappers;

import com.peoplespheres.domain.MappingDomain;
import com.peoplespheres.dto.MappingDTO;
import com.peoplespheres.dto.MappingFullDTO;
import com.peoplespheres.entites.*;
import com.peoplespheres.mappers.MappingMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the apping mapper")
 class MappingMapperTest {

    private MappingEntity mappingEntity;

    @Autowired
    private AdapterHelper helperClass;
    private MappingDomain mappingDomain;
    private MappingDTO mappingDTO;
    private MappingFullDTO mappingFullDTO;


    @BeforeEach
    private void setUp() {
        FlowEntity flowEntity = helperClass.getEntity_Helper("Entities/flowEntity.json", FlowEntity.class);
        DataSourceEntity dataSourceEntity = helperClass.getEntity_Helper("Entities/datasourceEntity.json", DataSourceEntity.class);
        DataTargetEntity dataTargetEntity = helperClass.getEntity_Helper("Entities/dataTargetEntity_single.json", DataTargetEntity.class);
        Collection<TransformationEntity> transformationEntities=helperClass.getEntities_Helper("Entities/transformationEntity_list.json",TransformationEntity.class);
        mappingEntity=new MappingEntity();
        mappingEntity.setFlow(flowEntity);
        mappingEntity.setTransformations(new HashSet<>(transformationEntities));
        mappingEntity.setTarget(dataTargetEntity);
        mappingEntity.setSource(dataSourceEntity);
        mappingDomain =  MappingMapper.fromEntityToDomain(mappingEntity);
        mappingDTO =  MappingMapper.fromDomainToDTO(mappingDomain);
        mappingFullDTO =  MappingMapper.fromDomainToFullDTO(mappingDomain);
    }
    @Test
    @DisplayName("Test the mapping between entity and domain instance ")
    void fromEntityToDomain_AndBackToEntity_Test() {
        // Test mapping between entity and domain instances
        assertThat(mappingEntity.getSource()).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                "name","parentAlias","parentFieldName","position","relatedPso"
                ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(mappingDomain.getDataSource());
        assertThat(mappingEntity.getTarget()).usingRecursiveComparison().comparingOnlyFields("id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(mappingDomain.getDataTarget());

    }
    @Test
    @DisplayName("Test the mapping between dto and domain instance ")
    void fromDtoToDomain_AndBackToDomain_Test() {
        // Test mapping between Domain and Dto instances
        assertThat(mappingDomain.getDataSource()).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(mappingDTO.getDataSource());
        assertThat(mappingDomain.getDataSource()).usingRecursiveComparison().comparingOnlyFields("id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(mappingDTO.getDataTarget());


        // Test mapping between Domain and FullDto instances
        assertThat(mappingDomain.getDataSource()).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(mappingFullDTO.getDataSource());
        assertThat(mappingDomain.getDataTarget()).usingRecursiveComparison().comparingOnlyFields("id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(mappingFullDTO.getDataTarget());

        // Test mapping between Dto and domain instances
        assertThat(mappingDTO).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(MappingMapper.fromDTOToDomain(mappingDTO));
        
        // Test mapping between FullDto and domain instances
        assertThat(mappingFullDTO.getDataSource()).usingRecursiveComparison().comparingOnlyFields("id","name","isArray","categoryAlias","categoryAlias",
                        "name","parentAlias","parentFieldName","position","relatedPso"
                        ,"isRequired","fieldType","dataflow_id","updated_at","deleted_at","version","created_at","clientId").
                isEqualTo(MappingMapper.fromFullDtoToDomain(mappingFullDTO).getDataSource());
        assertThat(mappingDomain.getDataTarget()).usingRecursiveComparison().comparingOnlyFields("id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(MappingMapper.fromFullDtoToDomain(mappingFullDTO).getDataTarget());
    }
}
