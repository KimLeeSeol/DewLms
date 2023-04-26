package com.kdew.dewlms.common.model;

import lombok.Data;

@Data
public class ResponseResult {

    ResponseReultHeader header;
    Object body;

    public ResponseResult(boolean result, String message) {
        header = new ResponseReultHeader(result, message);
    }

    public ResponseResult(boolean result) {
        header = new ResponseReultHeader(result);
    }
}
