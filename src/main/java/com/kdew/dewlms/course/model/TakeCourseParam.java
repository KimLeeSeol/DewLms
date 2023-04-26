package com.kdew.dewlms.course.model;

import com.kdew.dewlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

    long id;
    String status;
    String userId;

}
