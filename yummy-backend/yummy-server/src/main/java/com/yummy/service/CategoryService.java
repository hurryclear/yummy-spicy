package com.yummy.service;

import com.yummy.dto.CategoryDTO;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.result.PageResult;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
