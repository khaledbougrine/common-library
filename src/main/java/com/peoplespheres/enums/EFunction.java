package com.peoplespheres.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum EFunction {
    MAIN(""),
    DRAFT("draft/"),
    REPORT("report/");

    private final String folder;

    public static String[] names() {
        return Stream.of(EFunction.values()).map(EFunction::name).toArray(String[]::new);
    }
}
