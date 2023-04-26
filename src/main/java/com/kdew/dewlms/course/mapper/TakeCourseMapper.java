package com.kdew.dewlms.course.mapper;

import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.dto.TakeCourseDto;
import com.kdew.dewlms.course.model.CourseParam;
import com.kdew.dewlms.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {

    long selectListCount(TakeCourseParam parameter); // 멤버 수 구하기
    List<TakeCourseDto> selectList(TakeCourseParam parameter);

    List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);
}
