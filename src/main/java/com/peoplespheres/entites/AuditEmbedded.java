package com.peoplespheres.entites;

// Lombok imports

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/** Embeddable containing the usual auditable properties for the datasource */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditEmbedded {
    /** The timestamp of creation of the associated entity in the datasource */
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    /** The timestamp of last update of the associated entity in the datasource */
    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    /** The timestamp of deletion of the associated entity in the datasource */
    @Column(name = "deleted_at")
    private Instant deletedAt;
}