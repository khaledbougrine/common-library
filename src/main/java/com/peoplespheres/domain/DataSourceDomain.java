package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.util.Set;

/** Entity to manage the persistence of the PS data source in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class DataSourceDomain extends ADataDomain implements Serializable {
    private Set<TransformationDomain> transformations;
}
