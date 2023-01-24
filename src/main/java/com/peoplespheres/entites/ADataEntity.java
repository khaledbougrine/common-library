package com.peoplespheres.entites;

// Local imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peoplespheres.enums.FieldTypeEnum;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Entity to manage the persistence of the PS data source in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@NamedEntityGraph(
        name = "data.mapping.flow.psoType.relatedPsoType.transformations",
        subclassSubgraphs = {
                @NamedSubgraph(name = "subgraph.DataSource", type = DataSourceEntity.class, attributeNodes = { @NamedAttributeNode(value = "mapping") ,
                        @NamedAttributeNode(value = "psoType"), @NamedAttributeNode(value = "relatedPsoType")}),
                @NamedSubgraph(name = "subgraph.DataTarget", type = DataTargetEntity.class, attributeNodes = { @NamedAttributeNode(value = "mapping") ,
                        @NamedAttributeNode(value = "psoType"), @NamedAttributeNode(value = "relatedPsoType")})
        }
)
public abstract class ADataEntity extends AAuditableVersionedEntity {
    protected ADataEntity(@NonNull final ADataEntity aDataEntity) {
        this.id = null;
        this.categoryAlias = aDataEntity.getCategoryAlias();
        this.parentFieldName = aDataEntity.getParentFieldName();
        this.parentAlias = aDataEntity.getParentAlias();
        this.name = aDataEntity.getName();
        this.alias = aDataEntity.getAlias();
        this.isArray = aDataEntity.getIsArray();
        this.isRequired = aDataEntity.getIsRequired();
        this.fieldType = aDataEntity.getFieldType();
        this.defaultValue = aDataEntity.getDefaultValue();
        this.isActive = aDataEntity.getIsActive();
        this.client = aDataEntity.getClient();
        this.psoType = aDataEntity.getPsoType();
        this.relatedPsoType = aDataEntity.getRelatedPsoType();
        this.technicalId = aDataEntity.getTechnicalId();
    }

    /** The unique identifier of this PS data source associated to the row data */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    /** The alias of the category to which this data source is associated */
    @Column(name = "category_alias")
    @JsonProperty("category_alias")
    @Nullable
    private String categoryAlias;

    /** The name of the parent field to which this data source is associated */
    @Column(name = "parent_field_name")
    @JsonProperty("parent_field_name")
    @Nullable
    private String parentFieldName;

    /** The alias of the parent field to which this data source is associated */
    @Column(name = "parent_alias")
    @JsonProperty("parent_alias")
    @Nullable
    private String parentAlias;

    /** The name of the client database associated to this data source */
    @Column(name = "business_name")
    @Nullable
    private String name;

    /** The alias associated to this data source */
    @Column(name = "alias")
    @Nullable
    private String alias;

    /** Boolean flag indicating if it's an array or not */
    @Column(name = "is_array", nullable = false)
    @JsonProperty("is_array")
    @NotNull
    private Boolean isArray;

    /** Boolean flag indicating if the data target is required on partner side */
    @Column(name = "is_required", nullable = false)
    @JsonProperty("is_required")
    @NotNull
    private Boolean isRequired;

    /** The type of data received from PS global search API */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    @JsonProperty("type")
    @NotNull(message = "{data.type.not-null}")
    private FieldTypeEnum fieldType;

    /** The default value to give to this data source if not present in the PSO returned by PS global search API */
    @Column(name = "default_value")
    @Nullable
    private String defaultValue;

    /** Boolean flag indicating if the field is still active or deprecated */
    @NotNull(message = "{data.active.not-null}")
    @Column(name = "is_active", nullable = false)
    @JsonProperty("is_active")
    private Boolean isActive = false;

    /** Unique identifier to identify the partner data */
    @Nullable // Nullable for PS data but not for partner data
    @Column(name = "technical_id", updatable = false, length = 50, columnDefinition = "TEXT")
    private String technicalId;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    /** The client associated to this data */
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    protected ClientEntity client;

    /** The PSO type associated to this data */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "pso_type")
    @Nullable // Null if the source data is not PS but partner data
    protected PsoTypeEntity psoType;

    /** The PSO type associated to this data */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "related_pso_type")
    @JsonProperty("related_pso")
    @Nullable // Null if the source data is not PS but partner data
    protected PsoTypeEntity relatedPsoType;

    /** Checking if the mapping rule defined a default property value when not defined in client data.
     *
     * @return Boolean - Boolean flag indicating if this property mapping rule defined a default property value when not defined in client data.
     */
    @JsonIgnore
    public final Boolean hasDefaultValue() {
        return ((defaultValue != null) && ! defaultValue.isBlank());
    }

}
