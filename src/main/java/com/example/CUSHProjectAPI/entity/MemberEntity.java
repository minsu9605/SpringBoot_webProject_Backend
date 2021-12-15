package com.example.CUSHProjectAPI.entity;

import com.example.CUSHProjectAPI.dto.MemberDto;
import com.example.CUSHProjectAPI.enums.Gender;
import com.example.CUSHProjectAPI.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="MS_MEMBER")
@SequenceGenerator(
        name ="MEMBER_SEQ_GEN",
        sequenceName = "MS_MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
    @Column(name="MEMBER_ID")
    private Long id;

    @NotNull
    @Column(name="EMAIL")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NICKNAME")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name="GENDER")
    private Gender gender;

    @Column(name="BIRTH")
    private String birth;

    @Column(name="AGE")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public MemberDto toDto() {

        String[] str = birth.split("-");

        return MemberDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .nickname(nickname)
                .gender(gender)
                .year(str[0])
                .month(str[1])
                .day(str[2])
                .role(role)
                .build();
    }

    @Builder
    public MemberEntity(Long id, String username, String password, String nickname, Gender gender, String birth, int age, Role role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birth=birth;
        this.age = age;
        this.role = role;
    }

}
