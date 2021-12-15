package com.example.CUSHProjectAPI.entity;

import com.example.CUSHProjectAPI.dto.NoticeBoardDto;
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
@Table(name="MS_NOTICEBOARD")
@SequenceGenerator(
        name = "NOTICEBOARD_SEQ_GEN",
        sequenceName = "MS_NOTICEBOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class NoticeBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICEBOARD_SEQ_GEN")
    @Column(name = "NOTICE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_WRITER")
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

    @Column(name = "WRITE_IP")
    private String writeIp;

    public void setWriter(MemberEntity memberEntity){
        this.writer=memberEntity;
    }

    public NoticeBoardDto toDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return NoticeBoardDto.builder()
                .id(id)
                .title(title)
                .writer(writer.getNickname())
                .content(content)
                .createdDate(createdDate.format(formatter))
                .updatedDate(updatedDate.format(formatter))
                .hit(hit)
                .writeIp(writeIp)
                .build();
    }
    @Builder
    public NoticeBoardEntity(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate, int hit, String writeIp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.hit = hit;
        this.writeIp=writeIp;
    }
}
