package com.kdew.dewlms.member.controller;


import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.common.model.ResponseResult;
import com.kdew.dewlms.course.dto.TakeCourseDto;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.course.model.TakeCourseInput;
import com.kdew.dewlms.course.service.TakeCourseService;
import com.kdew.dewlms.member.model.MemberInput;
import com.kdew.dewlms.member.model.ResetPasswordInput;
import com.kdew.dewlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final TakeCourseService takeCourseService; // 현재 수강 목록을 가져오기 위해서

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal) {

        String userId = principal.getName();

        // 내가 신청한 강좌인지 확인
        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        // 나의 수강신청 정보가 아닐 때
        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}
