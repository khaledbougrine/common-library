package com.peoplespheres.exception;

// Spring imports

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/** Exception thrown when considered schedule is not valid */
public class ScheduleNotValidException extends Exception {
    public ScheduleNotValidException(@NotNull @PositiveOrZero final Long scheduleId, @Nullable final String reason) {
        super("FlowDomain '%s' not valid : %s".formatted(scheduleId, reason));
    }
}
