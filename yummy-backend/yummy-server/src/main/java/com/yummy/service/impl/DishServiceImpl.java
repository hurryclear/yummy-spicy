package com.yummy.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yummy.constant.MessageConstant;
import com.yummy.constant.StatusConstant;
import com.yummy.dto.DishDTO;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.entity.Dish;
import com.yummy.entity.DishFlavor;
import com.yummy.entity.Setmeal;
import com.yummy.exception.DeletionNotAllowedException;
import com.yummy.mapper.DishFlavorMapper;
import com.yummy.mapper.DishMapper;
import com.yummy.mapper.SetmealDishMapper;
import com.yummy.result.PageResult;
import com.yummy.service.DishService;
import com.yummy.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * add new dish with flavors
     * @param dishDTO
     */
    @Override
    @Transactional // what and why? we operate two tables: dish and dish_flavor, in order to
    // ensure the atomicity of our operation, we use this annotation
    public void addWithFlavors(DishDTO dishDTO) {
        // DishDTO contains dish and dish_flavor
        // table: dish and dish_flavor

        // add into dish table
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        // How to get the dish id after the new dish is added? #TODO: mybatis key-value return
        Long id = dish.getId();

        // add into dish_flavor table
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        // traverse the list of ids
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            // the dishes on sale can't be deleted
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            // the dishes bound with setmeal can't be deleted
            List<Long> setmeals = setmealDishMapper.getSetmealsByIds(ids);
            if (setmeals != null && setmeals.size() > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }
        // delete dishes by ids
        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }
}
