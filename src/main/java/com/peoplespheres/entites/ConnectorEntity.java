package com.peoplespheres.entites;

// Local imports

import com.peoplespheres.enums.ConnectorTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;

/** Entity to manage the persistence of connectors in the datasource */
@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "connector", schema = "public", indexes = {
        @Index(name = "idx_connectorentity_id_unq", columnList = "id", unique = true),
        @Index(name = "idx_connectorentity_client_id", columnList = "client_id"),
        @Index(name = "idx_connectorentity_id", columnList = "id, is_active, client_id", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_connectorentity_name_type", columnNames = {"name", "type", "module_id", "client_id"})
})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "connector.client.module",
        attributeNodes = {
                @NamedAttributeNode(value = "flows", subgraph = "flowWithIdAndActivity"),
                @NamedAttributeNode(value = "module", subgraph = "moduleWithId"),
                @NamedAttributeNode(value = "client", subgraph = "clientWithId")
        },
        subgraphs = {
                @NamedSubgraph(name="flowWithIdAndActivity", attributeNodes = {
                        @NamedAttributeNode(value = "id"),
                        @NamedAttributeNode(value = "isActive"),
                        @NamedAttributeNode(value = "isActiveVersion")
                }),
                @NamedSubgraph(name="moduleWithId", attributeNodes = {
                        @NamedAttributeNode(value = "id")
                }),
                @NamedSubgraph(name="clientWithId", attributeNodes = {
                        @NamedAttributeNode(value = "id")
                })
        })
public class ConnectorEntity extends AAuditableVersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{connector.name.not-empty}")
    private String name;

    @Column(name = "type", nullable = false)
    @NotNull(message = "{connector.type.not-null}")
    @Enumerated(EnumType.STRING)
    private ConnectorTypeEnum type;

    /** Boolean flag indicating if this workflow is active */
    @Column(name = "is_active", nullable = false)
    @NotNull(message = "{connector.active.not-null}")
    private Boolean isActive;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @OneToMany(mappedBy = "connector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<FlowEntity> flows = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "FK_CONNECTOR_MODULE"))
    private ModuleEntity module;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CONNECTOR_CLIENT"))
    private ClientEntity client;
}