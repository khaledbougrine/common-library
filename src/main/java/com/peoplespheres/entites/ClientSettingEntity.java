package com.peoplespheres.entites;

// Lombok imports

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** Entity to manage the persistence of client settings in the datasource */
@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_setting", schema = "public", indexes = {
        @Index(name = "idx_client_setting_id", columnList = "id"),
        @Index(name = "idx_client_setting", columnList = "dbname, env")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_client_setting", columnNames = {"dbname", "env"})
})
@EntityListeners(AuditingEntityListener.class)
public class ClientSettingEntity extends AAuditableVersionedEntity {

    @Id
    @JsonProperty(value = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Size(max = 255)
    @NotBlank(message = "{client.database.not-blank}")
    @Column(name = "dbname", nullable = false)
    private String dbname;

    @Size(max = 255)
    @NotBlank(message = "{client.env.not-blank}")
    @Column(name = "env", nullable = false)
    private String env;

    @Size(max = 255)
    @Column(name = "group_id")
    private String groupId;

    @Size(max = 255)
    @NotBlank(message = "{client.hostname.not-blank}")
    @Column(name = "hostname", nullable = false)
    private String hostname;

    ///////////////
    // RELATIONS //
    ///////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CLIENT_SETTING_CLIENT"))
    private ClientEntity client;
}