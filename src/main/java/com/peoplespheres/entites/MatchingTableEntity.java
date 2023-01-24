package com.peoplespheres.entites;

// Lombok imports

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** Entity to manage the matching business rules to apply to business properties depending on the business context to meet client needs */
@Entity
@Table(name = "matching_table", schema = "public", indexes = {
        @Index(name = "idx_matching_table_unq", columnList = "id", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@EntityListeners(AuditingEntityListener.class)
public class MatchingTableEntity extends AAuditableVersionedEntity {
    /** Unique identifier for the business rule associated to a dedicated property and a business context */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /** The key associated to the value filled for the considered property */
    @Column(name = "field_option_name", nullable = false)
    @NotBlank(message = "{matching.table.field.option.not-blank}")
    private String fieldOptionName;

    /** The field value */
    @Column(name = "field_value")
    @Nullable
    private String fieldValue;

    /** The associated value matching the PSO property to send to the client */
    @Column(name = "external_value", nullable = false)
    @NotBlank(message = "{matching.table.ext.value.not-blank}")
    private String externalValue;

    /** Boolean flag indicating if this workflow is active */
    @Column(name = "is_active", nullable = false)
    @NotNull(message = "{matching.table.is_active.not-null}")
    private Boolean isActive = true;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matching_file_id", foreignKey = @ForeignKey(name = "FK_MATCHING_TABLE_FILE"), nullable = false)
    @NotNull(message = "{matching.table.matching_file_id.not-null}")
    private MatchingTableFileEntity matchingFilename;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchingTableEntity)) return false;
        MatchingTableEntity that = (MatchingTableEntity) o;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getFieldOptionName(), that.getFieldOptionName()) && Objects.equal(getMatchingFilename(), that.getMatchingFilename());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFieldOptionName(), getMatchingFilename());
    }
}