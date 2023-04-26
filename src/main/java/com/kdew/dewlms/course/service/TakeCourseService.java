package com.kdew.dewlms.course.service;

import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.dto.TakeCourseDto;
import com.kdew.dewlms.course.model.*;

import java.util.List;

public interface TakeCourseService {


    /**
     * 수강 목록
     */
    List<TakeCourseDto> list(TakeCourseParam parameter);

    /**
     * 수강 내용 상태 변경
     */
    ServiceResult updateStatus(long id, String status);

    /**
     * 나의 수강 내역
     */
    List<TakeCourseDto> myCourse(String userId);

    /**
     * 수강 상세 정보
     */
    TakeCourseDto detail(long id);

    /**
     * 수강신청 취소 처리
     */
    ServiceResult cancel(long id);
}
