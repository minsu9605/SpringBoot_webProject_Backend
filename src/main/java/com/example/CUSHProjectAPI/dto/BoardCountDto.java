package com.example.CUSHProjectAPI.dto;

import com.example.CUSHProjectAPI.entity.BoardCountEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BoardCountDto {
    private Long id;
    private String statusName;
    private Long statusCount;
    private LocalDate batchDate;

    public BoardCountEntity toEntity() {
        return BoardCountEntity.builder()
                .id(id)
                .statusName(statusName)
                .statusCount(statusCount)
                .batchDate(batchDate)
                .build();
    }

    @Builder
    public BoardCountDto(Long id, String statusName, Long statusCount, LocalDate batchDate) {
        this.id=id;
        this.statusName=statusName;
        this.statusCount=statusCount;
        this.batchDate=batchDate;

    }
}
