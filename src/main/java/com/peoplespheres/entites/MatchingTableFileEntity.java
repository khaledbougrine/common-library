package com.peoplespheres.entites;

// Lombok imports

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

/** Entity to manage the matching business rules to apply to business properties depending on the business context to meet client needs */
@Entity
@Table(name = "matching_table_file", schema = "public", indexes = {
        @Index(name = "idx_matching_table_file_unq", columnList = "id", unique = true) },
        uniqueConstraints = {@UniqueConstraint(name = "uc_matching_table_file_entity", columnNames = {"mapping_id", "reference_alias"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "matchingTableFile.matchingTableEntries", attributeNodes = { @NamedAttributeNode(value = "matchingTableEntries") } )
public class MatchingTableFileEntity extends AAuditableVersionedEntity {
    /** Unique identifier for the matching table file entry */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /** The name of the considered property considered for the matching rule */
    @Column(name = "reference_alias", nullable = false)
    @NotBlank(message = "{matching.table.reference_alias.not-blank}")
    private String referenceAlias;

    /** The name of the matching table which is the reference for the considered property */
    @Column(name = "matching_table", nullable = false)
    @Nullable
    private String matchingTable;

    /** The name of the matching table file used to provide the content of the matching table in the frontend */
    @Column(name = "filename", nullable = false)
    @NotBlank(message = "{matching.table.filename.not-blank}")
    private String filename;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mapping_id", nullable = false)
    @NotNull
    private MappingEntity mapping;

    /** The set of matching values/entries contained by this matching table file */
    @OneToMany(mappedBy = "matchingFilename", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Size(min = 1, message = "{matchingFile.matchingEntries.not-empty}")
    private Set<MatchingTableEntity> matchingTableEntries = new LinkedHashSet<>();
}