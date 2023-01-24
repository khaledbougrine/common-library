package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.time.Instant;

/** Entity to manage the persistence of client settings in the datasource */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class ClientSettingDomain implements Serializable {
    Long id;
    String dbname;
    String env;
    String groupId;
    String hostname;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;
    String clientId;
}