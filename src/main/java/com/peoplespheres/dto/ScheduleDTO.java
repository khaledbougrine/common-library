package com.peoplespheres.dto;

// Local imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;

/** Projection of flow schedule */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represent a schedule row for a flow.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO implements Serializable {
    @Schema(description = "The unique identifier of the schedule")
    @NotNull(message="{schedule.id.not-null}")
    @PositiveOrZero(message="{schedule.id.positive}")
    private Long id;

    @Schema(description = "Boolean flag indicating if the scheduled flow associated is in maintenance mode", allowableValues = {"true", "false"})
    @NotNull(message="{schedule.maintenance.not-null}")
    private Boolean isMaintenance;

    @Schema(description = "The considered day of the week to be used to trigger the execution of the flow (ex: MONDAY)", example = "MONDAY",
            allowableValues = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "FIRST_DAY_OF_MONTH"}, nullable = true)
    @Nullable
    private String selectDay;

    @Schema(description = "The client local UTC start date and time from which this schedule is taken into account and the associated flow executed", nullable = true)
    @Nullable
    private Instant startDate;

    @Schema(description = "The client local UTC end date and time on which the schedule expire and after which it should no longer be taken into account", nullable = true)
    @Nullable
    private Instant endDate;

    @Schema(description = "The client local UTC time to be considered for the execution of the flow (ex: 14:00)", example = "14:00", nullable = true)
    private LocalTime executionTime;

    @Schema(description = "The periodicity/frequency at which the scheduled flow is regularly executed (ex: MONTHLY)", example = "MONTHLY",
            allowableValues = {"ONCE", "DAILY", "WEEKLY", "MONTHLY"})
    private FlowScheduleFrequencyEnum frequency;

    @Schema(description = "Scheduler repetition factor (ex: 2 for every 2 ...)", example = "2", nullable = true)
    @Nullable
    @Positive(message = "{schedule.repeated.positive}")
    private Integer repeated;

    @Schema(description = "Active flag")
    @NotNull(message="{schedule.active.not-null}")
    private Boolean isActive = true;

    @Schema(description = "Unique identifier of the associated client")
    @NotBlank(message="{schedule.client.id.not-blank}")
    @Size(min = 36,max = 36, message = "{schedule.client.id.uuid-format}")
    private String clientId;

    @Schema(description = "Unique identifier of the associated flow")
    @Nullable
    private Long flowId;
}
