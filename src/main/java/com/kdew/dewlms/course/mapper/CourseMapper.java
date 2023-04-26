package com.kdew.dewlms.course.mapper;

import com.kdew.dewlms.course.dto.CourseDto;
import com.kdew.dewlms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    long selectListCount(CourseParam parameter); // 멤버 수 구하기
    List<CourseDto> selectList(CourseParam parameter);
}
