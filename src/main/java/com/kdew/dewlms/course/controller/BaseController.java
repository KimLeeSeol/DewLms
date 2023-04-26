package com.kdew.dewlms.course.controller;

import com.kdew.dewlms.util.PageUtil;
import org.springframework.security.core.parameters.P;

public class BaseController {

    public String getPaperHtml(long totalCount, long pageSize, long pageIndex, String queryString) {
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);
        return pageUtil.pager();
    }
}
