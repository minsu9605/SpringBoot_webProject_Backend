package com.example.CUSHProjectAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public enum Gender {
    Male("Male", "남자"),
    Female("Female", "여자");

    private final String value;
    private final String title;

}
