package mappers;

import com.peoplespheres.domain.DataTargetDomain;
import com.peoplespheres.dto.DataTargetDTO;
import com.peoplespheres.dto.DataTargetFullDTO;
import com.peoplespheres.entites.DataTargetEntity;
import com.peoplespheres.entites.PsoTypeEntity;
import com.peoplespheres.mappers.DataTargetMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the data target mapper")
class DataTargetMapperTest {

    private DataTargetEntity dataTargetEntity;

    @Autowired
    private AdapterHelper helperClass;
    private PsoTypeEntity psoTypeEntity;


    @BeforeEach
    private void setUp() {
        dataTargetEntity = helperClass.getEntity_Helper("Entities/dataTargetEntity_single.json", DataTargetEntity.class);
        psoTypeEntity  = helperClass.getEntity_Helper("PsoTypes/pso_type.json", PsoTypeEntity.class);
        dataTargetEntity.setPsoType(psoTypeEntity);
        dataTargetEntity.setRelatedPsoType(psoTypeEntity);

    }

    @Test
    @DisplayName("Test the mapping for a DataTarget instance ")
    void DataTarget_instance_Test() {

        DataTargetDomain dataTargetDomain =  DataTargetMapper.fromEntityToDomain(dataTargetEntity);
        DataTargetDTO dataTargetDto =  DataTargetMapper.fromDomainToDTO(dataTargetDomain);
        DataTargetFullDTO dataTargetfullDto =  DataTargetMapper.fromDomainToFullDTO(dataTargetDomain);

        // Test mapping between entity and domain instances
        assertThat(dataTargetEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(dataTargetDomain);

        // Test mapping between Domain and Dto instances
        assertThat(dataTargetDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(dataTargetDto);

        // Test mapping between dto and Domain instances
        assertThat(dataTargetDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(DataTargetMapper.fromDtoToDomain(dataTargetDto));

        // Test mapping between domain and Entity instances
        assertThat(dataTargetEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(DataTargetMapper.fromDomainToEntity(dataTargetDomain));

        // Test mapping between FullDto  and Domain instances
        assertThat(dataTargetDomain).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(DataTargetMapper.fromFullDtoToDomain(dataTargetfullDto));


        // Test mapping between Domain  and FullDto instances
        assertThat(dataTargetfullDto).usingRecursiveComparison().comparingOnlyFields(
                        "id","categoryAlias","parentFieldName","parentAlias","name","alias","isArray",
                        "position","isRequired","fieldType","defaultValue","isActive","technicalId").
                isEqualTo(DataTargetMapper.fromDomainToFullDTO(dataTargetDomain));
    }


}
