package com.peoplespheres.enums;

// Lombok imports

import lombok.Getter;

import java.util.stream.Stream;

public enum ConnectorTypeEnum {
    IN("IN"),
    OUT("OUT"),
    INOUT("IN-OUT");

    @Getter
    private final String code;

    ConnectorTypeEnum(String code) {
        this.code = code;
    }

    public static ConnectorTypeEnum of(String type) {
        return Stream.of(values())
                .filter(p -> p.getCode().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
