package com.kdew.dewlms.course.service;

import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.model.CourseInput;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.model.ServiceResult;
import com.kdew.dewlms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    /**
     * 강좌 등록
     */
    boolean add(CourseInput parameter);

    /**
     * 강좌 정보 수정
     */
    boolean set(CourseInput parameter);

    /**
     * 강좌 목록
     */
    List<CourseDto> list(CourseParam parameter);

    /**
     * 강좌 상세 정보
     */
    CourseDto getById(long id);


    /**
     * 강좌 내용 삭제
     */
    boolean del(String idList);

    /**
     * 프론트 강좌 목록
     */
    List<CourseDto> frontList(CourseParam parameter);  // 강좌 정보 리턴

    /**
     * 프론트 강좌 상세 정보
     */
     CourseDto frontDetail(long id);

    /**
     * 수강신청
     */
    ServiceResult req(TakeCourseInput parameter);
}
