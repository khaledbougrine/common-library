package com.peoplespheres.domain;

// J2EE imports

import lombok.*;

import java.io.Serializable;
import java.util.Collection;

/** Entity to manage the persistence of clients in the datasource */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class MappingDomain implements Serializable {
    Long id;
    Integer position;
    Long flowId;
    DataSourceDomain dataSource;
    private Collection<TransformationDomain> transformations;
    DataTargetDomain dataTarget;
}