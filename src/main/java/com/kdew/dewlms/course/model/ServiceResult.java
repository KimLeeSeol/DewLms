package com.kdew.dewlms.course.model;

import com.kdew.dewlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class ServiceResult {

    boolean result; // 서비스가 true를 리턴할 것인지 false를 리턴할 것인지
    String message; // 만약 false면 그 에러 메세지는 무엇인지

    public ServiceResult() {
        result=true;
    }

    public ServiceResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
