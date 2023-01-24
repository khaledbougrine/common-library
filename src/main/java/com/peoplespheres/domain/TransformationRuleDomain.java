package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.RuleTypeEnum;

// Lombok imports
import lombok.*;

// J2EE imports
import java.io.Serializable;
import java.time.Instant;

/** Entity to manage the persistence of a specific transformation rule */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class TransformationRuleDomain implements Serializable {
    Integer id;
    String name;
    RuleTypeEnum function;
    Integer paramNumberMin;
    Integer paramNumberMax;
    Boolean isInternal;
    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;
}
