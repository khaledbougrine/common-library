package com.peoplespheres.entites;

// Lombok imports

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/** Entity class to manage the persistence of a population */
@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "population", schema = "public", indexes = {
        @Index(name = "idx_population_id_unq", columnList = "id", unique = true),
        @Index(name = "idx_population_client_alias", columnList = "client_id, name")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_populationentity_alias", columnNames = {"name", "client_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class PopulationEntity extends AAuditableVersionedEntity {
    public PopulationEntity(@NonNull final PopulationEntity referencePopulation) {
        this.id = null;
        this.name = referencePopulation.getName();
        this.alias = referencePopulation.getAlias();
        this.client = referencePopulation.getClient();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Size(max = 100)
    @NotBlank(message = "{population.name.not-blank}")
    private String name;

    @Column(name = "alias", nullable = false)
    @Size(max = 255)
    @NotNull(message = "{population.alias.not-blank}")
    private String alias;

    ///////////////
    // RELATIONS //
    ///////////////
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_POPULATION_CLIENT"))
    private ClientEntity client;

    @ManyToMany(mappedBy = "populations", fetch = FetchType.LAZY)
    private Set<FlowEntity> flowEntities = new LinkedHashSet<>();
}