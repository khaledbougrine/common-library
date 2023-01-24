package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

public enum MappingStatusEnum {
    TO_CONFIRM("TO_CONFIRM"),
    CONFIRMED("CONFIRMED");

    @Getter
    private final String code;

    MappingStatusEnum(@NotBlank String code) {
        this.code = code;
    }

    public static MappingStatusEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
