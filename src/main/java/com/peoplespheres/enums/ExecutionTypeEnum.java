package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

public enum ExecutionTypeEnum {
    REAL_TIME("REAL_TIME"),
    SCHEDULED("SCHEDULED");

    @Getter
    private final String code;

    ExecutionTypeEnum(@NotBlank String code) {
        this.code = code;
    }

    public static ExecutionTypeEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
