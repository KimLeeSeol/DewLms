package com.kdew.dewlms.course.controller;


import com.kdew.dewlms.admin.service.CategoryService;
import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.dto.TakeCourseDto;
import com.kdew.dewlms.course.model.CourseInput;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.course.model.TakeCourseParam;
import com.kdew.dewlms.course.service.CourseService;
import com.kdew.dewlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController{

    private final CourseService courseService;
    private final TakeCourseService takeCourseService;

    @GetMapping("/admin/takecourse/list.do")
    public String list(Model model, TakeCourseParam parameter
                    , BindingResult bindingResult) {

        parameter.init();

        List<TakeCourseDto> list = takeCourseService.list(parameter);


        long totalCount = 0;

        if (list != null && list.size() > 0) {
            totalCount = list.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml =  getPaperHtml(totalCount,parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        List<CourseDto> courseList = courseService.listAll();
        model.addAttribute("courseList",courseList);

        return "admin/takecourse/list";
    }

    @PostMapping("/admin/takecourse/status.do")
    public String status(Model model, TakeCourseParam parameter) {

        ServiceResult result = takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takecourse/list.do";
    }

}
