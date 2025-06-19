package com.yummy.service;

import com.yummy.dto.CategoryDTO;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.entity.Category;
import com.yummy.result.PageResult;
import io.swagger.models.auth.In;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(CategoryDTO categoryDTO);

    void changeStatus(Integer status, Long id);

    void deleteById(Long id);

    List<Category> list(Integer type);
}
