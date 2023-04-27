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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
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

    /**
     * 파일이 많아지면 관리하기 힘드니까 년도로 나누기!
     * files 밑에 년, 월이 생성되고
     * 년, 월 밑에 오리지널 파일명의 확장자와 랜덤하게 생성된 파일명이 저장됨
     */
    //파일명 가져오기
    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {
        //
        //
        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf("."); // .의 위치 가져오기
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1); // 파일 확장자 가져오기
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid); // 확장자가 없는 경우 파일 이름
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension; // 확장자가 있는 경우 파일 이름
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
                            , MultipartFile file
                            , CourseInput parameter) {

        String saveFilename = ""; // 파일 저장하기 위해서
        String urlFilename = "";

        if (file != null) {

            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "D:/zerobase/springboot/dewlms/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];


            try {
                File newfile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newfile));
            } catch (IOException e) {
                log.info("############");
                log.info(e.getMessage());
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);

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
