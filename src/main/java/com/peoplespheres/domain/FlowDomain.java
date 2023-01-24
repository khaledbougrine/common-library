package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.*;

// Lombok imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

// J2EE imports
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

/** Instance used by business layer to apply business logic and functionalities */
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class FlowDomain implements Serializable {
    Long id;
    String psoType;
    String name;
    FlowDirectionEnum type;
    String flowVersion;
    FlowStatusEnum status;
    MappingStatusEnum mappingStatus;
    ExecutionTypeEnum executionType;
    ExecutionStatusEnum executionStatus;
    OffsetDateTime executionStatusDate;
    Boolean isActive;
    Boolean isActiveVersion;
    Boolean isVisualised;
    OffsetDateTime validatedAt;
    Long connectorId;
    Long moduleId;
    String clientId;
    private Set<ScheduleDomain> schedules;
    private Set<DataSourceDomain> sources;
    private Set<DataTargetDomain> targets;
    private Set<TransformationDomain> transformations;
    private Set<MappingDomain> mappings;
    private Set<String> targetPopulations;
}

