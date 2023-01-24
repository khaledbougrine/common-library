package com.peoplespheres.domain;

import com.peoplespheres.entites.ScheduleEntity;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class ScheduleWithSeveralDaysDomain implements Serializable {
    private Set<Long> schedulesIds;
    private Instant endDate;
    private LocalTime executionTime;
    private FlowScheduleFrequencyEnum frequency;
    private Boolean isMaintenance;
    private Instant startDate;
    private Integer repeated;
    private String clientId;
    private Long flowId;
    private Set<String> selectDays;

    public boolean equalsEntity(ScheduleEntity scheduleEntity) {
        if (scheduleEntity == null) return false;
        return startDate.equals(scheduleEntity.getStartDate()) &&
                endDate.equals(scheduleEntity.getEndDate()) &&
                executionTime.equals(scheduleEntity.getExecutionTime()) &&
                frequency == scheduleEntity.getFrequency() &&
                isMaintenance.equals(scheduleEntity.getIsMaintenance());
    }



}
