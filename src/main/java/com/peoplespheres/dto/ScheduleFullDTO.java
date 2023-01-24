package com.peoplespheres.dto;

// Local imports

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Scheduler DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleFullDTO implements Serializable {
    public ScheduleFullDTO(@NonNull final ScheduleFullDTO scheduleFullDTO) {
        this.id = scheduleFullDTO.getId();
        this.startDate = scheduleFullDTO.getStartDate();
        this.endDate = scheduleFullDTO.getEndDate();
        this.frequency = scheduleFullDTO.getFrequency();
        this.isMaintenance = scheduleFullDTO.getIsMaintenance();
        this.selectDay = scheduleFullDTO.getSelectDay();
        this.executionTime = scheduleFullDTO.getExecutionTime();
        this.repeated = scheduleFullDTO.getRepeated();
        this.isActive = scheduleFullDTO.getIsActive();
        this.clientId = scheduleFullDTO.getClientId();
        this.flowId = scheduleFullDTO.getFlowId();
    }

    @Schema(description = "The unique identifier of the schedule", nullable = true)
    @Nullable // Nullable for new schedules
    @PositiveOrZero(message="{schedule.id.positive}")
    private Long id;

    @Schema(description = "Scheduler start date format : yyyy-MM-dd'T'hh:mm:ss", example = "2022-12-31T10:15:30", nullable = true)
    @Nullable
    private Instant startDate;

    @Schema(description = "Scheduler end date format : yyyy-MM-dd'T'hh:mm:ss", example = "2022-12-31T10:15:30", nullable = true)
    @Nullable
    private Instant endDate;

    @Schema(description = "The periodicity/frequency at which the scheduled flow is regularly executed (ex: MONTHLY)", example = "MONTHLY",
            allowableValues = {"ONCE", "DAILY", "WEEKLY", "MONTHLY"})
    private FlowScheduleFrequencyEnum frequency;

    @Schema(description = "Scheduler is maintenance active boolean: (true/false)")
    @NotNull(message="{schedule.maintenance.not-null}")
    private Boolean isMaintenance;

    @Schema(description = "The considered day of the week to be used to trigger the execution of the flow (ex: MONDAY)", example = "MONDAY",
            allowableValues = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"}, nullable = true)
    @Nullable
    private String selectDay;

    @Schema(description = "Scheduler execution time format : hh:mm:ss", example = "2022-12-31T10:15:30")
    @NotNull(message="{schedule.execution.time.not-null}")
    private LocalTime executionTime;

    @Schema(description = "Scheduler repetition factor (ex: 2 for every 2 ...)", example = "2", nullable = true)
    @Nullable
    private Integer repeated;

    @Schema(description = "Active flag")
    @NotNull(message="{schedule.active.not-null}")
    private Boolean isActive = true;

    @Schema(description = "Unique identifier of the associated client")
    @NotNull(message="{schedule.client.id.not-null}")
    @NotBlank(message="{schedule.client.id.not-blank}")
    private String clientId;

    @Schema(description = "Unique identifier of the associated flow")
    @NotNull(message="{schedule.flow.id.not-null}")
    @PositiveOrZero(message="{schedule.flow.id.positive}")
    private Long flowId;
}
