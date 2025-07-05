package com.yummy.service;

import com.yummy.dto.DishDTO;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.entity.Dish;
import com.yummy.result.PageResult;
import com.yummy.vo.DishVO;

import java.util.List;

public interface DishService {
    void addWithFlavors(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteByIds(List<Long> ids);

    DishVO getByIdWithFlavors(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    List<Dish> listByCategoryId(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
