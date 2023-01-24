package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.ConnectorTypeEnum;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.util.Set;

/** Entity to manage the persistence of connectors in the database */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
public class ConnectorDomain implements Serializable {
    Long id;
    String name;
    ConnectorTypeEnum type;
    Boolean isActive;
    private Set<FlowDomain> flows;
    ModuleDomain module;
    String clientId;
}
