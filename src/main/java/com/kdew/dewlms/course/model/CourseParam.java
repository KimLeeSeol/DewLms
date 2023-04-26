package com.kdew.dewlms.course.model;

import com.kdew.dewlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {

    long id; // course.id 자동 매핑
    long categoryId;
}
