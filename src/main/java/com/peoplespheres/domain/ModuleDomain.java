package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// Javax imports
import java.io.Serializable;

/** Instance used by business layer to apply business logic and functionalities */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class ModuleDomain implements Serializable {
    Long id;
    String name;
    String alias;
    String partnerName;
    Boolean isActive;
}