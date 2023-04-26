package com.kdew.dewlms.course.controller;


import com.kdew.dewlms.admin.dto.CategoryDto;
import com.kdew.dewlms.admin.service.CategoryService;
import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.model.CourseInput;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/course")
    public String course(Model model, CourseParam parameter) {

        // 카테고리id 값 가져오기


        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list); // list 목록이 이제 내려감



        int courseTotalCount = 0;
        List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());
        if (categoryList != null) {
            for(CategoryDto x : categoryList ) {
                courseTotalCount += x.getCourseCount();
            }
        }
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }

    @GetMapping("/course/{id}")
    public String courseDetail(Model model, CourseParam parameter) {

        CourseDto detail = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail",detail);


        return "course/detail";
    }
}
