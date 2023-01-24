package com.peoplespheres.entites;

// Lombok imports

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AAuditableVersionedEntity {
    @Embedded
    protected AuditEmbedded auditEmbedded;

    @Version
    protected Long version;

    ///////////////
    // CALLBACKS //
    ///////////////
    @PrePersist
    @PreUpdate
    protected void prePersistOrUpdateCallback() {
        if (auditEmbedded == null) {
            auditEmbedded = new AuditEmbedded();
            auditEmbedded.setCreatedAt(Instant.now());
        }
        else auditEmbedded.setUpdatedAt(Instant.now());
    }
}