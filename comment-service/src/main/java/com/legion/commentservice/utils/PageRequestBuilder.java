package com.legion.commentservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestBuilder {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    public PageRequest buildPageRequest(Integer page, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if(page != null && page > 0)
            queryPageNumber = page - 1;
        else queryPageNumber = DEFAULT_PAGE_NUMBER;

        if(pageSize == null) queryPageSize = DEFAULT_PAGE_SIZE;
        else {
            if (pageSize > 100) queryPageSize = 100;
            else queryPageSize = pageSize;
        }

        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

}
