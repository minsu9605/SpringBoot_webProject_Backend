package com.example.CUSHProjectAPI.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeBoardListDto {
    private int page;
    private int perPage;
    private String searchType;
    private String keyword;


    @Builder
    public NoticeBoardListDto(int page, int perPage, String searchType, String keyword) {
        this.page=page;
        this.perPage=perPage;
        this.searchType=searchType;
        this.keyword=keyword;

    }
}
