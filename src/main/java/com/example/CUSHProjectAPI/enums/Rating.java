package com.example.CUSHProjectAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rating {
    Five("Five", "★★★★★"),
    Four("Four","★★★★☆"),
    Three("Three","★★★☆☆"),
    Two("Two","★★☆☆☆"),
    One("One","★☆☆☆☆"),
    Zero("Zero","☆☆☆☆☆");

    private final String value;
    private final String title;

}
