package com.peoplespheres.entites;

// Local imports

import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalTime;

/** Entity to manage the persistence of schedules in the datasource through table "schedule" */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedule", indexes = {
        @Index(name = "IDX_SCHEDULE_ID", columnList = "id"),
        @Index(name = "IDX_SCHEDULE_CLIENT_ID", columnList = "id, client_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "UC_SCHEDULE", columnNames = {"start_date", "end_date", "frequency", "select_day", "execution_time", "repeated", "client_id", "flow_id"})
})
@EntityListeners(AuditingEntityListener.class)

public class ScheduleEntity extends AAuditableVersionedEntity {
    public ScheduleEntity(@NonNull final ScheduleEntity referenceSchedule) {
        this.startDate = referenceSchedule.getStartDate();
        this.endDate = referenceSchedule.getEndDate();
        this.frequency = referenceSchedule.getFrequency();
        this.isMaintenance = referenceSchedule.getIsMaintenance();
        this.selectDay = referenceSchedule.getSelectDay();
        this.executionTime = referenceSchedule.getExecutionTime();
        this.repeated = referenceSchedule.getRepeated();
        this.client = referenceSchedule.getClient();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "start_date")
    @Nullable
    private Instant startDate;

    @Column(name = "end_date")
    @Nullable
    private Instant endDate;

    @Column(name = "frequency", nullable = false)
    @NotNull
    private FlowScheduleFrequencyEnum frequency;

    @Column(name = "is_maintenance", nullable = false)
    @NotNull
    private Boolean isMaintenance;

    @Column(name = "select_day")
    @Nullable
    private String selectDay;

    @Column(name = "execution_time")
    @Nullable
    private LocalTime executionTime;

    @Column(name = "repeated")
    @Nullable
    private Integer repeated;

    //////////////////
    // ASSOCIATIONS //
    //////////////////
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SCHEDULE_CLIENT"))
    private ClientEntity client;

    /** The flow if present in case of scheduled workflow associated to this one */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", foreignKey = @ForeignKey(name = "FK_SCHEDULE_FLOW"))
    private FlowEntity flow;
}
