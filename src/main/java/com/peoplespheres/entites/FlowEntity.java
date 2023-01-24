package com.peoplespheres.entites;

// Local imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peoplespheres.enums.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Entity class to manage the persistence of a workflow */
@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "flow", schema = "public", indexes = {
        @Index(name = "IDX_FLOW_ID_UNQ", columnList = "id", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_flowentity_name_type", columnNames = {"name", "type", "flow_version", "client_id", "connector_id", "module_id", "status"})
})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "flow.populations.client.module.connector.schedules",
        attributeNodes = {@NamedAttributeNode(value = "populations", subgraph = "populationWithNames"),
                          @NamedAttributeNode(value = "client", subgraph = "clientWithId"),
                          @NamedAttributeNode(value = "module", subgraph = "moduleWithId"),
                          @NamedAttributeNode(value = "connector", subgraph = "connectorWithId"),
                          @NamedAttributeNode(value = "schedules", subgraph = "scheduleWithIds")},
        subgraphs = {
                @NamedSubgraph(name = "populationWithNames", attributeNodes = {@NamedAttributeNode(value = "name")}),
                @NamedSubgraph(name = "clientWithId", attributeNodes = {@NamedAttributeNode(value = "id")}),
                @NamedSubgraph(name = "moduleWithId", attributeNodes = {@NamedAttributeNode(value = "id")}),
                @NamedSubgraph(name = "connectorWithId", attributeNodes = {@NamedAttributeNode(value = "id")}),
                @NamedSubgraph(name = "scheduleWithIds", attributeNodes = {@NamedAttributeNode(value = "id")})
        }
)
public class FlowEntity extends AAuditableVersionedEntity {
    /** The unique identifier of this workflow (technical pk) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /** The PSO type associated to this workflow */
    @Column(name = "pso_type", nullable = false)
    @NotBlank(message = "{flow.pso-type.not-blank}")
    private String psoType;

    /** The name given to this workflow (label) */
    @Column(name = "name", nullable = false)
    @NotBlank(message = "{flow.name.not-blank}")
    private String name;

    /** The type from a data direction point of view of this workflow */
    @JsonProperty("direction")
    @Column(name = "type", nullable = false, length = 10)
    @NotNull(message = "{flow.direction.not-null}")
    @Enumerated(EnumType.STRING)
    private FlowDirectionEnum type;

    /** The version of the flow considered (Vx.y) */
    @JsonProperty("version")
    @Size(max = 10)
    @NotBlank(message = "{flow.version.not-blank}")
    @Column(name = "flow_version", nullable = false)
    private String flowVersion;

    /** The status of the definition for the mapping associated to this workflow */
    @JsonProperty("mapping_status")
    @Column(name = "mapping_status", nullable = false, length = 10)
    @NotNull(message = "{flow.mapping.status.not-null}")
    @Enumerated(EnumType.STRING)
    private MappingStatusEnum mappingStatus;

    /** The execution type associated to this workflow */
    @JsonIgnore
    @Column(name = "exe_type", length = 10)
    @Nullable
    @Enumerated(EnumType.STRING)
    private ExecutionTypeEnum executionType;

    /** The execution status associated to this workflow */
    @JsonIgnore
    @Column(name = "exe_status")
    @Nullable
    @Enumerated(value=EnumType.STRING)
    private ExecutionStatusEnum executionStatus;

    /** The execution status associated to this workflow */
    @Column(name = "exe_status_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Nullable
    private OffsetDateTime executionStatusDate;

    /** Boolean flag indicating if this workflow is active */
    @JsonProperty("is_active")
    @Column(name = "is_active", nullable = false)
    @NotNull(message = "{flow.active.not-null}")
    private Boolean isActive;

    /** Boolean flag indicating if this version of the flow is active */
    @JsonIgnore
    @NotNull(message = "{flow.version.active_version.not-null}")
    @Column(name = "is_active_version", nullable = false)
    private Boolean isActiveVersion = false;

    /** Boolean flag indicating if this version of the flow is active */
    @JsonProperty("is_visualised")
    @NotNull(message = "{flow.version.active.not-null}")
    @Column(name = "is_visualised", nullable = false)
    private Boolean isVisualised = false;

    /** The flow status. Default is draft. */
    @Column(name = "status", length = 10, nullable = false)
    @NotNull
    @Enumerated(value=EnumType.STRING)
    private FlowStatusEnum status;

    /** The validation date (when status is moved from draft to release). */
    @JsonIgnore
    @Column(name = "validated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Nullable
    private OffsetDateTime validatedAt;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FLOW_CLIENT"))
    @NotNull(message = "{flow.client.id.not-null}")
    private ClientEntity client;

    /** The link to the connector */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connector_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FLOW_CONNECTOR"))
    @NotNull(message = "{flow.connector.id.not-null}")
    private ConnectorEntity connector;

    /** The link to the module */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "FK_FLOW_MODULE"))
    @NotNull(message = "{flow.module.id.not-null}")
    private ModuleEntity module;

    /** The set of schedules for this flow. */
    @JsonIgnore
    @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ScheduleEntity> schedules = new LinkedHashSet<>();

    /** The set of mappings with transformations for this flow. */
    @JsonIgnore
    @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Set<MappingEntity> mappings = new LinkedHashSet<>();

    /** The link to the target populations of the flow */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "flow_population",
            joinColumns = @JoinColumn(name = "flow_id", foreignKey = @ForeignKey(name = "FK_FLOW_INTER_FLOW")),
            inverseJoinColumns = @JoinColumn(name = "population_id"), foreignKey = @ForeignKey(name = "FK_FLOW_INTER_POPULATION"))
    private Set<PopulationEntity> populations = new LinkedHashSet<>();

    /** Copy constructor used to clone a flow
     *
     * @param referenceFlowEntity
     */
    public FlowEntity(@NonNull final FlowEntity referenceFlowEntity) {
        this.id = null;
        this.psoType = referenceFlowEntity.getPsoType();
        this.name = referenceFlowEntity.getName();
        this.type = referenceFlowEntity.getType();
        this.flowVersion = referenceFlowEntity.getFlowVersion();
        this.status = FlowStatusEnum.DRAFT;
        this.mappingStatus = MappingStatusEnum.TO_CONFIRM;
        this.executionType = referenceFlowEntity.getExecutionType();
        this.isActive = true;
        this.isActiveVersion = true;
        this.isVisualised = false;
        this.client = referenceFlowEntity.getClient();
        this.connector = referenceFlowEntity.getConnector();
        this.module = referenceFlowEntity.getModule();
        this.setPopulations(new HashSet<>(referenceFlowEntity.getPopulations()));
        this.setSchedules(referenceFlowEntity.getSchedules().stream().filter(Objects::nonNull).map(schedule -> new ScheduleEntity(schedule)).collect(Collectors.toSet()));
        this.setMappings(referenceFlowEntity.getMappings().stream().filter(Objects::nonNull).map(mapping -> new MappingEntity(mapping)).collect(Collectors.toSet()));
    }


}

