package com.kdew.dewlms.course.controller;


import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.admin.service.CategoryService;
import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.entity.Course;
import com.kdew.dewlms.course.model.CourseInput;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.service.CourseService;
import com.kdew.dewlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();

        List<CourseDto> courseList = courseService.list(parameter);


        long totalCount = 0;

        /*if(CollectionUtils.isEmpty(courseList)){
            totalCount = courseList.get(0).getTotalCount();
        }*/

        if (courseList != null && courseList.size() > 0) {
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml =  getPaperHtml(totalCount,parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request
        , CourseInput parameter) {

        // 카테고리 정보를 내려줘야 함
        model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        if(editMode) {

            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id); // 강좌 상세 정보 가져옴

            if(existCourse == null) {
                // 강좌 정보가 없으면 에러 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
            // id가 존재하면 화면에 수정할 수 있도록 보여줌


        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
            , CourseInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if(editMode) {

            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id); // 강좌 상세 정보 가져옴

            if (existCourse == null) {
                // 강좌 정보가 없으면 에러 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);
            // id가 존재하면 화면에 수정할 수 있도록 보여줌
        } else {
            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }

    @PostMapping("/admin/course/delete.do")
    public String del(Model model, HttpServletRequest request
            , CourseInput parameter) {

        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }

}
