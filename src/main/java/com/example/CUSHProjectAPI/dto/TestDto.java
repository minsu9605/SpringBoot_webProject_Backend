package com.example.CUSHProjectAPI.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestDto {
    private int id;
    private String writer;


    @Builder
    public TestDto(int id, String writer) {
        this.id=id;
        this.writer=writer;

    }
}
