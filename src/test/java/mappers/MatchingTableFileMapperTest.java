package mappers;

import com.peoplespheres.model.PSObject;
import com.peoplespheres.entites.MatchingTableEntity;
import com.peoplespheres.entites.MatchingTableFileEntity;
import com.peoplespheres.mappers.MatchingTableFileMapper;
import configuration.WebMvcConfig;
import dto.AdapterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = WebMvcConfig.class)
@DisplayName("Unit test for the behaviour of the matching Table file mapper")
 class MatchingTableFileMapperTest {
    private MatchingTableFileEntity matchingTableFileEntity;

    private MatchingTableEntity matchingTableEntities;

    @Autowired
    private AdapterHelper helperClass;


    @BeforeEach
    private void setUp() {
        matchingTableFileEntity= helperClass.getEntity_Helper("Entities/matchingTableEntity.json", MatchingTableFileEntity.class);
        matchingTableEntities= helperClass.getEntity_Helper("Entities/matchingTableEntity.json", MatchingTableEntity.class);
        matchingTableFileEntity.setMatchingTableEntries(Set.of(matchingTableEntities));
    }

    @Test
    @DisplayName("Test the mapping for a matchingTableFile instance ")
    void matchingTableFile_instance_Test() {
        // Test mapping between entity and domain instances
        PSObject pSObject =  MatchingTableFileMapper.fromEntityToDTO(matchingTableFileEntity);
        assertThat(matchingTableFileEntity).usingRecursiveComparison().comparingOnlyFields(
                        "id","fieldAlias","fieldOptionName","fieldValue",
                        "externalValue","matchingTable","isActive").
                isEqualTo(pSObject);
    }
}
