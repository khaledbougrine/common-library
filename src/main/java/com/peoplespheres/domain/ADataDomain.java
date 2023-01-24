
package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.FieldTypeEnum;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.util.Set;

/** Entity to manage the persistence of the PS data source in the database */
@Setter
@Getter
public abstract class ADataDomain implements Serializable {
    Long id;
    String name;
    String alias;
    FieldTypeEnum fieldType;
    String categoryAlias;
    String defaultValue;
    Boolean isArray;
    String parentAlias;
    String parentFieldName;
    Boolean isRequired;
    String relatedPso;
    private Set<Long> transformationIds;
    String clientId;
    Integer psoTypeId;
    String psoTypeAlias;
    Integer relatedPsoTypeId;
    String technicalId;
}
