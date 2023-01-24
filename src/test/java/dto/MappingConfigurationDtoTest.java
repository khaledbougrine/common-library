package dto;

// Local imports
import com.peoplespheres.dto.MappingConfigurationDto;
import com.peoplespheres.enums.FieldTypeEnum;
import com.peoplespheres.enums.RuleTypeEnum;

// JUnit imports
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;

// AssertJ imports
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/** Unit test class testing the behaviour of the DTO's methods */
@DisplayName("Unit tests about business rule instances")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MappingConfigurationDtoTest {
    ///////////
    // TESTS //
    ///////////
    @Test
    @Order(1)
    @DisplayName("1 - Unit test testing a raw data business rule")
    void isRawDataBR() {
        // Build input raw data BR
        final MappingConfigurationDto rawDataBR = new MappingConfigurationDto(
            RuleTypeEnum.RAW_DATA, "Identity", "usr_first_name", "PRENOM", FieldTypeEnum.STRING, null, null);

        // Testing basic getters
        AssertionsForClassTypes.assertThat(rawDataBR.getBusinessRuleType()).as("Invalid rule type returned by getter").isEqualTo(RuleTypeEnum.RAW_DATA);
        assertThat(rawDataBR.getBusinessRuleCategory()).as("Invalid category returned by getter").isEqualTo("Identity");
        assertThat(rawDataBR.getBusinessRulePsoProperty()).as("Invalid PSO property returned by getter").isEqualTo("usr_first_name");
        assertThat(rawDataBR.getBusinessRuleExternalProperty()).as("Invalid external property returned by getter").isEqualTo("PRENOM");
        AssertionsForClassTypes.assertThat(rawDataBR.getExternalFieldType()).as("Invalid external field type returned by getter").isEqualTo(FieldTypeEnum.STRING);
        assertThat(rawDataBR.getBusinessRuleDetails()).as("Invalid business rules details returned by getter").isNull();
        assertThat(rawDataBR.getBusinessRuleFormat()).as("Invalid business rule format returned by getter").isNull();

        // Testing the type of the business rule
        assertThat(rawDataBR.isRawDataBR()).as("Business rule should be considered as a Raw Data one").isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("2 - Unit test testing a substring business rule")
    void isSubstrBR() {
        // Build input substring BR
        final MappingConfigurationDto substringBR = new MappingConfigurationDto(
            RuleTypeEnum.SUBSTRING, "Personal information", "usr_social_security_number", "SSNUME", FieldTypeEnum.STRING, "2##15", null);

        // Testing basic getters
        AssertionsForClassTypes.assertThat(substringBR.getBusinessRuleType()).as("Invalid rule type returned by getter").isEqualTo(RuleTypeEnum.SUBSTRING);
        assertThat(substringBR.getBusinessRuleCategory()).as("Invalid category returned by getter").isEqualTo("Personal information");
        assertThat(substringBR.getBusinessRulePsoProperty()).as("Invalid PSO property returned by getter").isEqualTo("usr_social_security_number");
        assertThat(substringBR.getBusinessRuleExternalProperty()).as("Invalid external property returned by getter").isEqualTo("SSNUME");
        AssertionsForClassTypes.assertThat(substringBR.getExternalFieldType()).as("Invalid external field type returned by getter").isEqualTo(FieldTypeEnum.STRING);
        assertThat(substringBR.getBusinessRuleDetails()).as("Invalid business rules details returned by getter").isEqualTo("2##15");
        assertThat(substringBR.getBusinessRuleFormat()).as("Invalid business rule format returned by getter").isNull();

        // Testing the type of the business rule
        assertThat(substringBR.isSubstrBR()).as("Business rule should be considered as a Substring one").isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("3 - Unit test testing a format business rule")
    void isFormatBR() {
        // Build input format BR
        final MappingConfigurationDto formatBR = new MappingConfigurationDto(
            RuleTypeEnum.FORMAT, "Personal information", "usr_employment_contract_contract_effective_date", "DEBCON", FieldTypeEnum.STRING, null, "dd/MM/yyyy");

        // Testing basic getters
        AssertionsForClassTypes.assertThat(formatBR.getBusinessRuleType()).as("Invalid rule type returned by getter").isEqualTo(RuleTypeEnum.FORMAT);
        assertThat(formatBR.getBusinessRuleCategory()).as("Invalid category returned by getter").isEqualTo("Personal information");
        assertThat(formatBR.getBusinessRulePsoProperty()).as("Invalid PSO property returned by getter").isEqualTo("usr_employment_contract_contract_effective_date");
        assertThat(formatBR.getBusinessRuleExternalProperty()).as("Invalid external property returned by getter").isEqualTo("DEBCON");
        AssertionsForClassTypes.assertThat(formatBR.getExternalFieldType()).as("Invalid external field type returned by getter").isEqualTo(FieldTypeEnum.STRING);
        assertThat(formatBR.getBusinessRuleDetails()).as("Invalid business rules details returned by getter").isNull();
        assertThat(formatBR.getBusinessRuleFormat()).as("Invalid business rule format returned by getter").isEqualTo("dd/MM/yyyy");

        // Testing the type of the business rule
        assertThat(formatBR.isFormatBR()).as("Business rule should be considered as a Format one").isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("4 - Unit test testing a matching table business rule")
    void isMatchingTableBR() {
        // Build input format BR
        final MappingConfigurationDto matchingTableBR = new MappingConfigurationDto(
            RuleTypeEnum.MATCHING_TABLE, "Personal information", "usr_birth_country", "PAYNAI", FieldTypeEnum.STRING, null, null);

        // Testing basic getters
        AssertionsForClassTypes.assertThat(matchingTableBR.getBusinessRuleType()).as("Invalid rule type returned by getter").isEqualTo(RuleTypeEnum.MATCHING_TABLE);
        assertThat(matchingTableBR.getBusinessRuleCategory()).as("Invalid category returned by getter").isEqualTo("Personal information");
        assertThat(matchingTableBR.getBusinessRulePsoProperty()).as("Invalid PSO property returned by getter").isEqualTo("usr_birth_country");
        assertThat(matchingTableBR.getBusinessRuleExternalProperty()).as("Invalid external property returned by getter").isEqualTo("PAYNAI");
        AssertionsForClassTypes.assertThat(matchingTableBR.getExternalFieldType()).as("Invalid external field type returned by getter").isEqualTo(FieldTypeEnum.STRING);
        assertThat(matchingTableBR.getBusinessRuleDetails()).as("Invalid business rules details returned by getter").isNull();
        assertThat(matchingTableBR.getBusinessRuleFormat()).as("Invalid business rule format returned by getter").isNull();

        // Testing the type of the business rule
        assertThat(matchingTableBR.isMatchingTableBR()).as("Business rule should be considered as a Matching table one").isTrue();
    }

    @Test
    @Order(5)
    @DisplayName("5 - Unit test testing a concatenation business rule")
    void isConcatenationBR() {
        // Build input format BR
        final MappingConfigurationDto concatenationBR = new MappingConfigurationDto(
            RuleTypeEnum.CONCATENATION, "Personal information", "usr_display_name", "DISPLAY_NAME", FieldTypeEnum.STRING, "1", null);

        // Testing basic getters
        AssertionsForClassTypes.assertThat(concatenationBR.getBusinessRuleType()).as("Invalid rule type returned by getter").isEqualTo(RuleTypeEnum.CONCATENATION);
        assertThat(concatenationBR.getBusinessRuleCategory()).as("Invalid category returned by getter").isEqualTo("Personal information");
        assertThat(concatenationBR.getBusinessRulePsoProperty()).as("Invalid PSO property returned by getter").isEqualTo("usr_display_name");
        assertThat(concatenationBR.getBusinessRuleExternalProperty()).as("Invalid external property returned by getter").isEqualTo("DISPLAY_NAME");
        AssertionsForClassTypes.assertThat(concatenationBR.getExternalFieldType()).as("Invalid external field type returned by getter").isEqualTo(FieldTypeEnum.STRING);
        assertThat(concatenationBR.getBusinessRuleDetails()).as("Invalid business rules details returned by getter").isEqualTo("1");
        assertThat(concatenationBR.getBusinessRuleFormat()).as("Invalid business rule format returned by getter").isNull();

        // Testing the type of the business rule
        assertThat(concatenationBR.isConcatenationBR()).as("Business rule should be considered as a Concatenation one").isTrue();
    }
}
