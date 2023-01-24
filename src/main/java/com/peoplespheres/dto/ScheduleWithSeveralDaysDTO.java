package com.peoplespheres.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Schedule With Several Days  DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleWithSeveralDaysDTO extends ScheduleFullDTO {


    @Schema(description = "list of schedules IDs", example = "[1,2,3]", nullable = true)
    @Nullable
    private Set<Long> schedulesIds;

    @Schema(description = "The considered day of the week or the dates in a month to be used to trigger the execution of the flow. ", example = "[ 'MONDAY' , 'TUESDAY', 'WEDNESDAY' ,'THURSDAY' ] or [ '12/1/2022' , '12/01/2013 ']")
    @NotNull(message = "{schedule.selectDays.not-null}")
    private Set<String> selectDays;


}

