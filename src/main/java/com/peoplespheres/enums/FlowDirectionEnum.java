package com.peoplespheres.enums;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

/**
 * Enumeration to be used for the direction of the business flow.
 * in -> Coming from the client and going to PS
 * out -> Coming from PS and to be sent to the client
 */
public enum FlowDirectionEnum {
    IN("IN"),
    OUT("OUT");

    @Getter
    private final String code;

    FlowDirectionEnum(@NotBlank String code) {
        this.code = code;
    }

    public static FlowDirectionEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
