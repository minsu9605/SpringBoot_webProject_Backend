package com.example.CUSHProjectAPI.dto;

import com.example.CUSHProjectAPI.entity.MemberEntity;
import com.example.CUSHProjectAPI.enums.Gender;
import com.example.CUSHProjectAPI.enums.Role;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Gender gender;
    private String year;
    private String month;
    private String day;
    private Role role;
    private String birth;
    private int age;


    //나이 계산
    public int calAge(String birth){
        LocalDate now = LocalDate.now();
        LocalDate BirthDate = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));   //1996.05.31

        int age = now.minusYears(BirthDate.getYear()).getYear() +1; // 2021-1996 +1 = 26
        return age;
    }

    public MemberEntity toEntity() {

        return MemberEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .nickname(nickname)
                .gender(gender)
                .birth(year +  "-" + month + "-" +day)
                .age(calAge(year + month +day))
                .role(role)
                .build();
    }

    @Builder
    public MemberDto(Long id, String username, String password, String nickname, Gender gender, String year, String month, String day, Role role, String birth, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname=nickname;
        this.gender=gender;
        this.year=year;
        this.month=month;
        this.day=day;
        this.role=role;
        this.birth=year +  "-" + month + "-" +day;
        this.age=calAge(year + month +day);
    }


}
