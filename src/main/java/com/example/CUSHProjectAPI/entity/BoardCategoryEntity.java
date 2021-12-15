package com.example.CUSHProjectAPI.entity;

import com.example.CUSHProjectAPI.dto.BoardCategoryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name="MS_BCATEGORY")
@SequenceGenerator(
        name = "BCATEGORY_SEQ_GEN",
        sequenceName = "MS_BCATEGORY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BoardCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BCATEGORY_SEQ_GEN")
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME")
    private String name;

    public BoardCategoryDto toDto(){
        return BoardCategoryDto.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Builder
    public BoardCategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
