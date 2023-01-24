package com.peoplespheres.entites;

// Lombok imports

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
@Table(name = "mapping", schema = "public", indexes = {
        @Index(name = "IDX_MAPPING_ID_UNQ", columnList = "id", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_mappingentity_flow_id", columnNames = {"flow_id", "source_id", "target_id"})
})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "mapping.flow.source",
        attributeNodes = {
                @NamedAttributeNode(value = "flow"),
                @NamedAttributeNode(value = "source", subgraph = "subgraphDataSource")
        },
        subgraphs = {
                @NamedSubgraph(name="subgraphDataSource", attributeNodes = {
                        @NamedAttributeNode(value = "psoType"),
                        @NamedAttributeNode(value = "relatedPsoType")
                })
        }
)
@NamedEntityGraph(name = "mapping.flow.target",
        attributeNodes = {
                @NamedAttributeNode(value = "flow"),
                @NamedAttributeNode(value = "target")
        }
)
@NamedEntityGraph(name = "mapping.flow.source.target.transformations",
        attributeNodes = {
                @NamedAttributeNode(value = "flow"),
                @NamedAttributeNode(value = "source", subgraph = "subgraphDataSource"),
                @NamedAttributeNode(value = "target"),
                @NamedAttributeNode(value = "transformations", subgraph = "transformationWithRule")
        },
        subgraphs = {
                @NamedSubgraph(name="transformationWithRule", attributeNodes = {
                        @NamedAttributeNode(value = "transformationRule")
                }),
                @NamedSubgraph(name="subgraphDataSource", attributeNodes = {
                        @NamedAttributeNode(value = "psoType"),
                        @NamedAttributeNode(value = "relatedPsoType")
                })
        }
)
public class MappingEntity extends AAuditableVersionedEntity {
    /** The unique identifier of this workflow (technical pk) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /** Integer indicating the descending position of the property in the form within the PS Web App */
    @Column(name = "position", nullable = false)
    @NotNull(message = "{mapping.position.not-null}")
    @PositiveOrZero(message = "{mapping.position.positive}")
    private Integer position;

    /** The flow associated to this data */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    protected FlowEntity flow;

    /** Data source for the mapping. */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", foreignKey = @ForeignKey(name = "FK_MAPPING_DATA_SOURCE"))
    private DataSourceEntity source;

    /** Data target for the mapping. */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(name = "FK_MAPPING_DATA_TARGET"))
    private DataTargetEntity target;

    /** The set of sources with transformations for this flow. */
    @OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<TransformationEntity> transformations = new LinkedHashSet<>();

    public MappingEntity(@NonNull final MappingEntity referenceMappingEntity) {
        this.id = null;
        this.setPosition(referenceMappingEntity.getPosition());
        this.setSource(referenceMappingEntity.getSource());
        this.setTarget(referenceMappingEntity.getTarget());
        this.setTransformations(referenceMappingEntity.getTransformations().stream().filter(Objects::nonNull).map(TransformationEntity::new).collect(Collectors.toSet()));
    }
}

