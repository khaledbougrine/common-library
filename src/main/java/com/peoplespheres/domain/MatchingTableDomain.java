package com.peoplespheres.domain;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;

/** Entity to manage the persistence of the matching table values */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
public class MatchingTableDomain implements Serializable {
    Long id;
    String externalValue;
    String fieldAlias;
    String fieldOptionName;
    String fieldValue;
    String matchingTable;
    Boolean isActive;
    Long flowId;
    String clientId;
}
