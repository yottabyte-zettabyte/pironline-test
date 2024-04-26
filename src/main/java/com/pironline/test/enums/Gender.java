package com.pironline.test.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    M("Male"),
    F("Female");

    private final String description;
}
