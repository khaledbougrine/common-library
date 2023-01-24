package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

/** Enumeration to be used for the type of mapping rule to apply a transformation to a property. */
public enum RuleTypeEnum {
    RAW_DATA("RAW_DATA"),
    RAW_GENERATION("RAW_GENERATION"),
    CONCATENATION("CONCATENATION"),
    FORMAT("FORMAT"),
    CONSTANT("CONSTANT"),
    MATCHING_TABLE("MATCHING_TABLE"),
    MATCHING_TABLE_GENERATION("MATCHING_TABLE_GENERATION"),
    SUBSTRING("SUBSTRING"),
    TRANSTYPING("TRANSTYPING"),
    UPPERCASE("UPPERCASE"),
    LOWERCASE("LOWERCASE");

    @Getter
    private final String code;

    RuleTypeEnum(@NotBlank String code) {
        this.code = code;
    }

    public static RuleTypeEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
