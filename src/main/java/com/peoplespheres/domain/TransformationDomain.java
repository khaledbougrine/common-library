package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;

/** Entity to manage the persistence of the PS data source in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class TransformationDomain implements Serializable {
    Long id;
    String name;
    String parameters;
    Integer position;
    Long sourceId;
    Long targetId;
    Long moduleId;
    Long flowId;
    String clientId;
    TransformationRuleDomain transformationRule;
}
