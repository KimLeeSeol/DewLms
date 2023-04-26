package com.kdew.dewlms.common.model;

import lombok.Data;

@Data
public class ResponseReultHeader {

    boolean result;
    String message;

    public ResponseReultHeader(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseReultHeader(boolean result) {
        this.result = result;
    }
}
