package com.peoplespheres.entites;

// Lombok imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.peoplespheres.enums.RuleTypeEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/** Entity to manage the persistence of the transformations available to transform the data source to provide the expected data target */
@Entity(name = "transformation")
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transformation")
@EntityListeners(AuditingEntityListener.class)
@Slf4j
@NamedEntityGraph(name="graph.fullTransformation", attributeNodes = { @NamedAttributeNode(value = "transformationRule") })
@EqualsAndHashCode
public class TransformationEntity extends AAuditableVersionedEntity {
    public TransformationEntity(@NonNull final TransformationEntity referenceTransformation) {
        this.parameters = referenceTransformation.getParameters();
        this.position = referenceTransformation.getPosition();
        this.name = referenceTransformation.getName();
        this.isActive = referenceTransformation.getIsActive();
        this.module = referenceTransformation.getModule();
        this.transformationRule = referenceTransformation.getTransformationRule();
        this.mapping = referenceTransformation.getMapping();
    }

    /** The unique identifier associated to this transformation */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /** A pipe separated list of parameters given to the associated transformation rule */
    @Column(name = "parameters", length = 1000)
    @Nullable
    private String parameters;

    /** The position of application of the associated transformation */
    @Column(name = "position")
    @NotNull(message = "{transformation.position.not-null}")
    @PositiveOrZero(message = "{transformation.position.positive}")
    private Integer position;

    /** The name given to this transformation */
    @Column(name = "name")
    @Nullable
    private String name;

    /** Boolean flag indicating if this transformation is active */
    @Column(name = "is_active", nullable = false)
    @NotNull(message = "{transformation.active.not-null}")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransformationEntity)) return false;
        TransformationEntity that = (TransformationEntity) o;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getParameters(), that.getParameters()) && Objects.equal(getName(), that.getName()) && Objects.equal(getMapping(), that.getMapping()) && Objects.equal(getModule(), that.getModule()) && Objects.equal(getTransformationRule(), that.getTransformationRule());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

//////////////////
    // ASSOCIATIONS //
    //////////////////
    /** The flow associated to this data */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapping_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSFORMATION_FLOW"))
    @NotNull
    private MappingEntity mapping;

    /** The module associated to this data */
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSFORMATION_MODULE"))
    private ModuleEntity module;

    /** The set of transformation rules composing this data transformation */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformation_rule_id", foreignKey = @ForeignKey(name = "FK_TRANSFORMATION_TRANSFORMATION_RULE"))
    @Nullable
    private TransformationRuleEntity transformationRule;


    ////////////
    // PUBLIC //
    ////////////
    /** Test if the mapping business rule is a raw data transformation consisting in modifying only the key of the properties
     * in the payload to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a raw data mapping business rule.
     */
    @JsonIgnore
    public final boolean isRawDataBR(){
        return ((transformationRule == null) || (transformationRule.getFunction().equals(RuleTypeEnum.RAW_DATA)));
    }

    /** Test if the mapping business rule is a substring transformation consisting in replacing the value of the properties
     * in the payload with a substring of this property value to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a substring mapping business rule.
     */
    @JsonIgnore
    public final boolean isSubstrBR(){
        return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.SUBSTRING)));
    }

    /** Test if the mapping business rule is a format transformation consisting in replacing the value of the properties
     * in the payload with a different formatted value of this property value to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a substring mapping business rule.
     */
    @JsonIgnore
    public final boolean isFormatBR(){
        return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.FORMAT)));
    }

    /** Test if the mapping business rule is a matching table transformation consisting in replacing the value of the properties
     * in the payload with another one extracted from a dictionary in the database to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a matching table mapping business rule.
     */
    @JsonIgnore
    public final boolean isMatchingTableBR(){
        return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.MATCHING_TABLE)));
    }

    /** Test if the mapping business rule is a format concatenation consisting in replacing the value of the properties
     * in the payload with a concatenation of other property values in this payload to make it compliant with the partner needs and requirements.
     *
     * @return boolean - Flag indicating if the mapping is a concatenation mapping business rule.
     */
    @JsonIgnore
    public final boolean isConcatenationBR(){
        return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.CONCATENATION)));
    }

    /** Test if the mapping business rule is a raw generation consisting in computing the value from a static aliased value independent of the PSO itself.
     *
     * @return boolean - Flag indicating if the mapping is a raw generation business rule.
     */
    @JsonIgnore
    public boolean isRawGenerationBR() { return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.RAW_GENERATION))); }

    /** Test if the mapping business rule is a matching table generation consisting in computing the matching value from a static aliased value independent of the PSO itself.
     *
     * @return boolean - Flag indicating if the mapping is a matching table generation business rule.
     */
    @JsonIgnore
    public boolean isMatchingTableGenerationBR() { return ((transformationRule != null) && (transformationRule.getFunction().equals(RuleTypeEnum.MATCHING_TABLE_GENERATION))); }

    @JsonIgnore
    public RuleTypeEnum getTransformationType(){
        return ( transformationRule == null) ? RuleTypeEnum.RAW_DATA : transformationRule.getFunction();
    }
}
