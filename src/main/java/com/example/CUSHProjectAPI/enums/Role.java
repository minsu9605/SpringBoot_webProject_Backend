package com.example.CUSHProjectAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_MEMBER("ROLE_MEMBER", "회원");

    private final String value;
    private final String title;

}
