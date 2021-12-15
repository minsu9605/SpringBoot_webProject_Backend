package com.example.CUSHProjectAPI.dto;

import com.example.CUSHProjectAPI.entity.BoardCategoryEntity;
import lombok.*;

@Data
@NoArgsConstructor
public class BoardCategoryDto {
    private Long id;
    private String name;

    public BoardCategoryEntity toEntity() {
        return BoardCategoryEntity.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Builder
    public BoardCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;

    }
}
