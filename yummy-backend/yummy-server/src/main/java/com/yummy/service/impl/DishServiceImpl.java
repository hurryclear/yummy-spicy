package com.yummy.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yummy.dto.DishDTO;
import com.yummy.entity.Dish;
import com.yummy.entity.DishFlavor;
import com.yummy.mapper.DishFlavorMapper;
import com.yummy.mapper.DishMapper;
import com.yummy.service.DishService;
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
    @Override
    @Transactional // what and why?
    public void add(DishDTO dishDTO) {
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
}
