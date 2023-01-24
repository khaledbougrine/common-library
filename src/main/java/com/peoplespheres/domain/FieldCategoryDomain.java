package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;

/** Entity to manage the persistence of the PSO fields in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class FieldCategoryDomain implements Serializable {
     Integer id;
     String alias;
     Boolean isActive;
     String name;
}