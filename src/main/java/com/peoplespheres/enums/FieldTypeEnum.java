package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.stream.Stream;

/** Enumeration to be used for the expected external field type. */
public enum FieldTypeEnum {
    ALIAS("alias"),
    TEXT("text"),
    HIERARCHY("hierarchy"),
    CHECKBOX("checkbox"),
    MULTI_CHECKBOX("multi_checkbox"),
    RADIO_BUTTON("radio_button"),
    COMPOSITE("composite"),
    CALCULATED("calculated"),
    CURRENCY("currency"),
    RANGE("range"),
    NAMED_FILE("named_file"),
    TAG("tag"),
    URL("url"),
    NUMBER("number"),
    DATETIME("datetime"),
    DATE("date"),
    IMAGE("image"),
    USER_RELATION("user_relation"),
    GROUP("group"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    MODULES("modules"),
    DOMAIN("domain"),
    EXTERNAL_ID("external_id"),
    LIST("list"),
    STRING("string");

    @Getter
    private final String code;

    FieldTypeEnum(@NotBlank String code) {
        this.code = code;
    }

    public static FieldTypeEnum of(@NotBlank String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}




