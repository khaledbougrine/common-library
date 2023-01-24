package com.peoplespheres.entites;

// Spring imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "module", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uc_moduleentity_alias", columnNames = {"alias", "partner_name", "client_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class ModuleEntity extends AAuditableVersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotBlank(message="module.name.not-blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotBlank(message="module.alias.not-blank")
    @Column(name = "alias", nullable = false)
    private String alias;

    @JsonProperty("partner_name")
    @Size(max = 255)
    @NotBlank(message="module.partner.name.not-blank")
    @Column(name = "partner_name", nullable = false)
    private String partnerName;

    /** Boolean flag indicating if this module is active */
    @JsonProperty("is_active")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
}