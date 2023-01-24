package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

public enum ExecutionStatusEnum {
    STOPPED("STOPPED"),
    RUNNING("RUNNING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    @Getter
    private final String code;

    ExecutionStatusEnum(String code) {
        this.code = code;
    }

    public static ExecutionStatusEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
