package com.kdew.dewlms.course.controller;


import com.kdew.dewlms.admin.service.CategoryService;
import com.kdew.dewlms.common.model.ResponseResult;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.course.model.TakeCourseInput;
import com.kdew.dewlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController{

    private final CourseService courseService;

    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal) {

        parameter.setUserId(principal.getName());

        ServiceResult result = courseService.req(parameter);
        if(!result.isResult()) {

            ResponseResult responseResult = new ResponseResult(false, result.getMessage());

            return ResponseEntity.ok().body(responseResult);
        }
        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);

    }
}
