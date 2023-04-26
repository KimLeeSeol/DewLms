package com.kdew.dewlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode{
    @Id
    private String userId;

    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt; // 회원가입 날짜
    private LocalDateTime uptDt; // 회원정보 수정 날짜


    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt; // 이메일 인증 날짜
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt; // 마지노선 비밀번호 유효 기간

    private boolean adminYn;

    private String userStatus; // 회원 상태 : 이용 가능한 상태, 정지 상태

    private String zipcode; // 우편번호
    private String addr; // 주소
    private String addrDetail; // 상세 주소
}
