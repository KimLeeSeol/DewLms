package com.kdew.dewlms.course.dto;

import com.kdew.dewlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDto {

    Long id;
    long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents; // 데이터가 많이 저장되기 때문
    long price;
    long salePrice;
    LocalDate saleEndDt;
    LocalDateTime regDt; // 등록 날짜
    LocalDateTime uptDt; // 수정 날짜

    long totalCount;
    long seq;

    public static CourseDto of(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .uptDt(course.getUptDt())
                .build();
    }

    public static List<CourseDto> of(List<Course> courses) {

        if (courses == null) {
            return null;
        }

        List<CourseDto> courseList = new ArrayList<>();
        for(Course x : courses) {
            courseList.add(CourseDto.of(x));
        }
        return courseList;
    }
}
