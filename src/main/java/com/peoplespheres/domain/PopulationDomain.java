package com.peoplespheres.domain;


// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.time.Instant;

/** Entity class to manage the persistence of a population */
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class PopulationDomain implements Serializable {
    Long id;
    String name;
    String alias;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;
    private ClientDomain client;
    Integer memberCount = 0;
}