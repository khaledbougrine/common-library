package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

public enum FlowStatusEnum {
    DRAFT("DRAFT"),
    RELEASE("RELEASE");

    @Getter
    private final String code;

    FlowStatusEnum(@NotBlank String code) {
        this.code = code;
    }

    public static FlowStatusEnum of(@NotBlank String type) {
        if (type == null) return null;
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
