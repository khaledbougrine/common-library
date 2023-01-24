package com.peoplespheres.entites;

// Lombok imports

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field_category", schema = "public", indexes = {
        @Index(name = "field_category_alias_unique", columnList = "alias", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_fieldcategoryentity_alias", columnNames = {"alias"})
})
@EntityListeners(AuditingEntityListener.class)
public class FieldCategoryEntity extends AAuditableVersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotBlank(message = "{psoType.creation.date.not-null}")
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("alias", alias)
                .append("isActive", isActive)
                .append("name", name)
                .append("created_at", getAuditEmbedded().getCreatedAt())
                .append("updatedAt", getAuditEmbedded().getUpdatedAt())
                .append("deletedAt", getAuditEmbedded().getDeletedAt())
                .append("version", getVersion())
                .toString();
    }

}