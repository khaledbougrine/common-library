package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.FieldTypeEnum;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.time.Instant;

/** Entity to manage the persistence of the PSO fields in the database */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class FieldDomain implements Serializable {
     Long id;
     String alias;
     String name;
     FieldTypeEnum type;
     Instant createdAt;
     Instant updatedAt;
     Instant deletedAt;
     FieldCategoryDomain category;
     String psoTypeAlias;
     String psoTypeName;
     Integer psoTypeId;
     Boolean isArray;
}