package com.yummy.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    // employee name (not required)
    private String name;
    // page number (required)
    // I used pageNumber and it doesn't work, why? #TODO: don't use pageNumber
    // Most frontend libraries (like Axios, Element UI tables, Ant Design tables, etc.) use
    // conventional query param names like: ?page=1&pageSize=10
    private int page;
    // page size (required)
    private int pageSize;
}
