package com.example.CUSHProjectAPI.entity;

import com.example.CUSHProjectAPI.dto.BoardDto;
import com.example.CUSHProjectAPI.enums.Rating;
import com.example.CUSHProjectAPI.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Entity
@Table(name="MS_BOARD")
@SequenceGenerator(
        name = "BOARD_SEQ_GEN",
        sequenceName = "MS_BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class BoardEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GEN")
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Board_WRITER")
    private MemberEntity writer;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "UPDATEDDATE")
    private LocalDateTime updatedDate;

    @Column(name = "HIT")
    private int hit;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "WRITE_IP")
    private String writeIp;

    @Column(name = "MYLAT")
    private double myLat;

    @Column(name = "MYLNG")
    private double myLng;

    @Column(name = "ALERTREAD")
    private int alertRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "RATING")
    private Rating rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private BoardCategoryEntity category;

    public void setCategory(BoardCategoryEntity category){
        this.category=category;
    }
    public void setWriter(MemberEntity memberEntity){
        this.writer=memberEntity;
    }

    public BoardEntity setStatus(Status status){
        this.status=status;
        return this;
    }

    public BoardDto toDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return BoardDto.builder()
                .id(id)
                .title(title)
                .writer(writer.getNickname())
                .content(content)
                .createdDate(createdDate.format(formatter))
                .updatedDate(updatedDate.format(formatter))
                .hit(hit)
                .price(price)
                .rating(rating)
                .status(status)
                .categoryName(category.getName())
                .categoryId(category.getId())
                .writeIp(writeIp)
                .myLat(myLat)
                .myLng(myLng)
                .alertRead(alertRead)
                .build();
    }
    @Builder
    public BoardEntity(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate,int hit, int price, Rating rating, Status status,String writeIp, double myLat, double myLng,int alertRead ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.hit = hit;
        this.price = price;
        this.rating = rating;
        this.status=status;
        this.writeIp = writeIp;
        this.myLat = myLat;
        this.myLng = myLng;
        this.alertRead = alertRead;
    }
}