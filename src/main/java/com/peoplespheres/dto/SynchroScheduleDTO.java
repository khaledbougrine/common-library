package com.peoplespheres.dto;

// Swagger imports

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Scheduler DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SynchroScheduleDTO extends ScheduleFullDTO {
    public SynchroScheduleDTO(final ScheduleFullDTO scheduleFullDTO) {
        super(scheduleFullDTO);
    }

    @Schema(description = "Client name")
    @NotBlank
    private String clientName;

    @Schema(description = "Flow id")
    @NotNull(message="{schedule.flow.id.not-null}")
    @PositiveOrZero(message="{schedule.flow.id.positive}")
    private Long flowId;

    @Schema(description = "Flow name")
    @NotBlank
    private String flowName;

    @Schema(description = "Flow status")
    @NotBlank
    private String flowStatus;

    @Schema(description = "Module id")
    @NotNull(message="{schedule.module.id.not-null}")
    @PositiveOrZero(message="{schedule.module.id.positive}")
    private Long moduleId;

    @Schema(description = "Module name")
    @NotBlank
    private String moduleName;
}
