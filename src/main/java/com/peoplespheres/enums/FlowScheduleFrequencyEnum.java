package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

public enum FlowScheduleFrequencyEnum {
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    DAILY("DAILY"),
    ONCE("ONCE");

    @Getter
    private final String code;

    FlowScheduleFrequencyEnum(@NotBlank String code) {
        this.code = code;
    }

    public static FlowScheduleFrequencyEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
