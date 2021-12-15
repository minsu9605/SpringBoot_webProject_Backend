package com.example.CUSHProjectAPI.entity;

import com.example.CUSHProjectAPI.dto.BoardCommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name="MS_BCOMMENT")
@SequenceGenerator(
        name = "BCOMMENT_SEQ_GEN",
        sequenceName = "MS_BCOMMENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BoardCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BCOMMENT_SEQ_GEN")
    @Column(name = "CID")
    private Long id;

    @Column(name = "CONTENT")
    private String comment;

    @Column(name = "CREATEDATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATEDATE")
    private LocalDateTime updateDate;

    @Column(name = "CDEPTH")
    private int cDepth;

    @Column(name = "CGROUP")
    private Long cGroup;

    @ManyToOne
    @JoinColumn(name = "COMMENT_WRITER")
    private MemberEntity writer;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private BoardEntity boardId;

    public void setCommentWriter(MemberEntity memberEntity){
        this.writer=memberEntity;
    }

    public void setCommentBoardId(BoardEntity boardEntity){
        this.boardId=boardEntity;
    }

    public BoardCommentDto toDto() {
        return BoardCommentDto.builder()
                .id(id)
                .comment(comment)
                .createDate(createDate)
                .updateDate(updateDate)
                .writer(writer.getNickname())
                .boardId(boardId.getId())
                .cDepth(cDepth)
                .cGroup(cGroup)
                .build();
    }


    @Builder
    public BoardCommentEntity(Long id, String comment, LocalDateTime createDate, LocalDateTime updateDate, int cDepth, Long cGroup) {
        this.id = id;
        this.comment = comment;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.cDepth = cDepth;
        this.cGroup = cGroup;
    }
}
