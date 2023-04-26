package com.kdew.dewlms.course.model;

import lombok.Data;

@Data
public class CourseInput {

    long id;
    long categoryId;
    String subject;
    String keyword;
    String summary;
    String contents;
    long price;
    long salePrice;
    String saleEndDtText; // 날짜형으로 받아올 수 있는지 모르니까 일단 문자열로 받아서 날짜형으로 바꾸기!

    // 삭제
    String idList;
}
