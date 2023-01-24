package com.peoplespheres.domain;

// Local imports
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;

// Lombok imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

// J2EE imports
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;

/** Entity to manage the persistence of schedules in the database */
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class ScheduleDomain implements Serializable {
    Long id;
    String selectDay;
    Instant endDate;
    LocalTime executionTime;
    FlowScheduleFrequencyEnum frequency;
    Boolean isMaintenance;
    Instant startDate;
    Integer repeated;
    String clientId;
    Long flowId;
}
