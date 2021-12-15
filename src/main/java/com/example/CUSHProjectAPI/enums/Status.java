package com.example.CUSHProjectAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    sell("sell", "판매중"),
    soldOut("soldOut","판매완료"),
    old("old","오래된 판매중 게시물");

    private final String value;
    private final String title;
}
