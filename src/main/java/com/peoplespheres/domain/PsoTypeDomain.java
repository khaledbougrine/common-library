package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.util.Set;

/** Entity to manage the persistence of the pso types in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class PsoTypeDomain implements Serializable {
     Integer id;
     String alias;
     String name;
     private Set<FieldCategoryDomain> fieldCategories;
}