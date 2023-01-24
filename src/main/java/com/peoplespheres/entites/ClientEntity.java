package com.peoplespheres.entites;

// Hibernate imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client", schema = "public", indexes = {
        @Index(name = "client_id_uindex", columnList = "id", unique = true),
        @Index(name = "client_alias_uindex", columnList = "alias", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_cliententity_name_alias", columnNames = {"name", "alias"})
})
@NamedEntityGraph(name = "client.clientSettings.connectors",
        attributeNodes = {
                @NamedAttributeNode(value = "clientSettings"),
                @NamedAttributeNode(value = "connectors")
        })
@EntityListeners(AuditingEntityListener.class)
public class ClientEntity extends AAuditableVersionedEntity {
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name= "UUIDGenerator", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, length = 36, columnDefinition = "TEXT")
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @Size(max = 255)
    @JsonIgnore
    @Column(name = "description")
    private String description;

    @NotNull
    @JsonProperty("is_enabled")
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = false;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ClientSettingEntity> clientSettings = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ConnectorEntity> connectors = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PopulationEntity> populations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PsoTypeEntity> psoTypes = new LinkedHashSet<>();
}