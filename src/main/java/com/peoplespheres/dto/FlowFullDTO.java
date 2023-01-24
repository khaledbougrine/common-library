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
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Schema(description = "Client flow properties")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowFullDTO implements Serializable {
    @Schema(description = "Unique identifier of this flow", nullable = true)
    @Nullable
    private Long id;

    @Schema(description = "PSO type associated to this flow (ex: usr)", example = "usr")
    @NotBlank(message="{flow.pso-type.not-blank}")
    private String psoType;

    @Schema(description = "Flow name (label)", example = "User")
    @NotBlank(message="{flow.name.not-blank}")
    private String name;

    @Schema(description = "Flow type", allowableValues = {"OUT", "IN"})
    @NotNull(message = "{flow.type.not-null}")
    private FlowDirectionEnum type;

    @Schema(description = "Flow version (ex:1.01)", example = "1.0")
    @NotBlank(message="{flow.version.not-blank}")
    private String flowVersion;

    @Schema(description = "Flow mapping status", allowableValues = {"TO_CONFIRM", "CONFIRMED"})
    private MappingStatusEnum mappingStatus;

    @Schema(description = "Flow execution type ", allowableValues = {"REAL_TIME", "SCHEDULED"})
    private ExecutionTypeEnum executionType;

    @Schema(description = "Flow execution status ", allowableValues = {"SUCCESS", "FAILED", "RUNNING", "STOPPED"})
    private ExecutionStatusEnum executionStatus;

    @Schema(description = "Flow version", allowableValues = {"DRAFT", "RELEASE"})
    private FlowStatusEnum status;

    @Schema(description = "Is this flow currently activated ?")
    @NotNull(message = "{flow.active.not-null}")
    private Boolean isActive;

    @Schema(description = "Is this flow current version activated ?")
    @NotNull(message = "{flow.active.not-null}")
    private Boolean isActiveVersion;

    @Schema(description = "Has this flow already been visualised ?")
    @NotNull(message = "{flow.visualised.not-null}")
    private Boolean isVisualised = false;

    @Schema(description = "Schedules associated to this flow", nullable = true)
    @Nullable
    private List<ScheduleFullDTO> schedules;

    @Schema(description = "List of target populations", nullable = true)
    @Nullable
    private List<String> targetPopulations;

    @Schema(description = "List of the mappings associated to this flow", nullable = true)
    @Nullable
    private List<MappingFullDTO> mappings;
}
