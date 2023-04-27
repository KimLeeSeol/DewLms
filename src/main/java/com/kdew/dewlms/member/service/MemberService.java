package com.kdew.dewlms.member.service;


import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.admin.model.MemberParam;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.member.model.MemberInput;
import com.kdew.dewlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 입력받은 uuid에 대해서 password로 초기화 함
     */
    boolean resetPassword(String id, String password);

    /**
     * 입력받은 uuid값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);

    /**
     * 회원 목록 리턴(관리자 페이지에서만 사용 가능)
     */
    List<MemberDto> list(MemberParam parameter);

    /**
     * 회원 상세 정보
     */
    MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원정보 수정
     */
    ServiceResult updateMember(MemberInput parameter);

    /**
     * 회원 정보 페이지 내에 비밀번호 변경 기능
     */
    ServiceResult updateMemberPassword(MemberInput parameter);

    /**
     * 회원을 탈퇴시켜줌
     */
    ServiceResult withdraw(String userId, String password);
}
