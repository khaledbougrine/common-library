package com.peoplespheres.dto;

// Swagger imports
import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;

/** DTO data transport object for flow data */
@Getter
@Setter
@Schema(description = "Represent a flow row for a connector.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowDTO {
    @Schema(description = "Unique identifier of this flow", nullable = true)
    @Nullable
    private Long id;

    @Schema(description = "PSO type associated to this flow (ex: usr)", example = "usr")
    @NotBlank(message="{flow.pso-type.not-blank}")
    private String psoType;

    @Schema(description = "Flow name (label)", example = "User")
    @NotBlank(message="{flow.name.not-blank}")
    private String name;

    @Schema(description = "Type", allowableValues = {"IN", "OUT"})
    @NotNull(message="{flow.type.not-null}")
    private FlowDirectionEnum type;

    @Schema(description = "Flow version (ex:1.01)", example = "1.0")
    @NotBlank(message="{flow.version.not-blank}")
    private String flowVersion;

    @Schema(description = "Status", allowableValues = {"DRAFT", "RELEASE"})
    private FlowStatusEnum status;

    @Schema(description = "Mapping status", allowableValues = {"TO_CONFIRM", "CONFIRMED"}, nullable = true)
    private MappingStatusEnum mappingStatus;

    @Schema(description = "Execution type", allowableValues = {"REAL_TIME", "SCHEDULED"})
    private ExecutionTypeEnum executionType;

    @Schema(description = "Execution status", allowableValues = {"RUNNING", "SUCCESS", "FAILED", "STOPPED"}, nullable = true)
    private ExecutionStatusEnum executionStatus;

    @Schema(description = "Execution status associated date (format ISO 8601)", example = "2022-09-02T13:42:27.784490-04:00", nullable = true)
    @Nullable
    private String executionStatusDate;

    @Schema(description = "Is this flow currently activated ?")
    @NotNull(message = "{flow.active.not-null}")
    private Boolean isActive = true;

    @Schema(description = "Has this flow already been visualised ?")
    @NotNull(message = "{flow.visualised.not-null}")
    private Boolean isVisualised = false;

    @Schema(description = "Unique identifier of the client owning this flow", nullable = true)
    @NotBlank(message="{flow.client.id.not-blank}") // Mandatory but not mandatory for the FE
    @Size(min = 36,max = 36, message = "{flow.client.id.uuid-format}")
    private String clientId;

    @Schema(description = "Unique identifiers of the schedules for this flow", nullable = true)
    @Nullable
    private Set<Long> scheduleIds;

    @Schema(description = "Unique identifier of the associated connector")
    @NotNull(message="{flow.connector.id.not-null}")
    @PositiveOrZero(message="{flow.connector.id.positive}")
    private Long connectorId;

    @Schema(description = "Unique identifier of the targeted partner module")
    @NotNull(message="{flow.module.id.not-null}")
    @PositiveOrZero(message="{flow.module.id.positive}")
    private Long moduleId;

    @Schema(description = "List of target populations", nullable = true)
    @Nullable
    private Set<String> targetPopulations;
}
