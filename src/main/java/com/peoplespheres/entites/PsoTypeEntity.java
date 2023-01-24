package com.peoplespheres.entites;

// Lombok imports

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pso_type", schema = "public", indexes = {
        @Index(name = "pso_types_alias_unique", columnList = "alias", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_psotypeentity_alias", columnNames = {"alias", "client_id"})
})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "psoTypes.client.fields.category",
        attributeNodes = {
            @NamedAttributeNode(value = "client"),
            @NamedAttributeNode(value = "fields", subgraph = "fieldWithCategoryPsoType")
        },
        subgraphs = {
                @NamedSubgraph(name="fieldWithCategoryPsoType", attributeNodes = {
                        @NamedAttributeNode(value = "category"),
                        @NamedAttributeNode(value = "psoType"),
                })
        })
public class PsoTypeEntity extends AAuditableVersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Size(max = 3)
    @NotNull
    @Column(name = "alias", nullable = false, length = 3)
    private String alias;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    ///////////////
    // RELATIONS //
    ///////////////
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PSO_TYPE_CLIENT"))
    private ClientEntity client;

    @OneToMany(mappedBy = "psoType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<FieldEntity> fields = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PsoTypeEntity that = (PsoTypeEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}