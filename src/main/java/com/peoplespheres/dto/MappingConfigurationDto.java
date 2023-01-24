package com.peoplespheres.dto;

// Local imports
import com.peoplespheres.enums.FieldTypeEnum;
import com.peoplespheres.enums.RuleTypeEnum;

// Lombok imports
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Javax imports
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// J2EE imports
import java.io.Serializable;

/** DTO projection used to extract the mapping business rule from the mapping table */
@AllArgsConstructor
@Getter
public class MappingConfigurationDto implements Serializable {
    /** The type of the considered mapping business rule. */
    private final RuleTypeEnum businessRuleType;

    /** The category associated to the mapping business rule */
    private final String businessRuleCategory;

    /** The property associated to the mapping business rule */
    @NotEmpty
    private final String businessRulePsoProperty;

    /** The external client property name associated to the PSO property name */
    @NotEmpty
    private final String businessRuleExternalProperty;

    public FieldTypeEnum getExternalFieldType() {
        return externalFieldType;
    }

    /** Boolean flag indicating if this property is mandatory for the considered client in this context */
    @NotNull
    @Getter(AccessLevel.NONE)
    private FieldTypeEnum externalFieldType;

    /** The details about the mapping business rule expected for this property to meet the client needs */
    private final String businessRuleDetails;

    /** The format expected for this property in case of a FORMAT transformation to meet the client needs */
    private final String businessRuleFormat;

    /** Test if the mapping business rule is a raw data transformation consisting in modifying only the key of the properties
     * in the payload to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a raw data mapping business rule.
     */
    public final boolean isRawDataBR(){
        return (getBusinessRuleType().equals(RuleTypeEnum.RAW_DATA));
    }

    /** Test if the mapping business rule is a substring transformation consisting in replacing the value of the properties
     * in the payload with a substring of this property value to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a substring mapping business rule.
     */
    public final boolean isSubstrBR(){
        return (getBusinessRuleType().equals(RuleTypeEnum.SUBSTRING));
    }

    /** Test if the mapping business rule is a format transformation consisting in replacing the value of the properties
     * in the payload with a different formatted value of this property value to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a substring mapping business rule.
     */
    public final boolean isFormatBR(){
        return (getBusinessRuleType().equals(RuleTypeEnum.FORMAT));
    }

    /** Test if the mapping business rule is a matching table transformation consisting in replacing the value of the properties
     * in the payload with another one extracted from a dictionary in the database to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a matching table mapping business rule.
     */
    public final boolean isMatchingTableBR(){
        return (getBusinessRuleType().equals(RuleTypeEnum.MATCHING_TABLE));
    }

    /** Test if the mapping business rule is a format concatenation consisting in replacing the value of the properties
     * in the payload with a concatenation of other property values in this payload to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a concatenation mapping business rule.
     */
    public final boolean isConcatenationBR(){
        return (getBusinessRuleType().equals(RuleTypeEnum.CONCATENATION));
    }
}
