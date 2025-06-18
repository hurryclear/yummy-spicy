package com.yummy.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.yummy.constant.StatusConstant;
import com.yummy.context.BaseContext;
import com.yummy.dto.CategoryDTO;
import com.yummy.entity.Category;
import com.yummy.mapper.CategoryMapper;
import com.yummy.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        // categoryDTO: id, type, name, sort

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        category.setStatus(1); // what's the value by default?
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }
}
