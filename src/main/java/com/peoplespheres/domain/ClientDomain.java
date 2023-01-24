package com.peoplespheres.domain;


// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.util.Set;

/** Entity to manage the persistence of clients in the datasource */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class ClientDomain implements Serializable {
    String id;
    String name;
    String alias;
    String description;
    Boolean isEnabled;
    private Set<ClientSettingDomain> clientSettings;
    private Set<ConnectorDomain> connectors;
    private Set<PopulationDomain> populations;
    private Set<PsoTypeDomain> psoTypes;
}