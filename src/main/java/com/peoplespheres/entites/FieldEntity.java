package com.peoplespheres.entites;

// Local imports

import com.peoplespheres.enums.FieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** Entity to manage the persistence of client fields in the datasource */
@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field", schema = "public", indexes = {
        @Index(name = "field_alias_uindex", columnList = "alias", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_fieldentity_alias", columnNames = {"alias"})
})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "field.psoType.category",
        attributeNodes = {
                @NamedAttributeNode(value = "category"),
                @NamedAttributeNode(value = "psoType")
        })
public class FieldEntity extends AAuditableVersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(value=EnumType.STRING)
    @NotNull(message = "{data.type.not-null}")
    private FieldTypeEnum type;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @Column(name = "is_array", nullable = false, columnDefinition = "boolean default false")
    private Boolean isArray = false;

    ///////////////
    // RELATIONS //
    ///////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pso_type_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FIELD_PSO_TYPE"))
    private PsoTypeEntity psoType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_FIELD_CATEGORY"))
    private FieldCategoryEntity category;
}