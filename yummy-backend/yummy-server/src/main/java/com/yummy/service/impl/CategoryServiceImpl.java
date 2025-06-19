package com.yummy.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yummy.constant.MessageConstant;
import com.yummy.constant.StatusConstant;
import com.yummy.context.BaseContext;
import com.yummy.dto.CategoryDTO;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.entity.Category;
import com.yummy.exception.DeletionNotAllowedException;
import com.yummy.mapper.CategoryMapper;
import com.yummy.mapper.DishMapper;
import com.yummy.mapper.SetmealMapper;
import com.yummy.result.PageResult;
import com.yummy.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * add category
     * @param categoryDTO
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        // categoryDTO: id, type, name, sort

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        category.setStatus(1); // what's the value by default?
        category.setStatus(StatusConstant.DISABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    /**
     * page query
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        // get the page through PageHelper and page contains total and results
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * update
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = Category.builder()
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.update(category);
    }

    /**
     * change status
     * @param status
     * @param id
     * how is "id" passed from frontend?
     */
    @Override
    public void changeStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
//                .updateUser(BaseContext.getCurrentId())
//                .updateTime(LocalDateTime.now())
                .build();
        categoryMapper.update(category);
    }


    /**
     * delete by id
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        // you must be careful when you want to delete a category
        // the category could be bound with dishes or setmeals
        // when you delete it, it will affect other data
        // you have to search in dishes and setmeals if they are bound with this category id
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count = setmealMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.deleteById(id);

    }
}
