package com.yummy.service;

import com.yummy.dto.DishDTO;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.result.PageResult;
import com.yummy.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    void addWithFlavors(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteByIds(List<Long> ids);

    DishVO getByIdWithFlavors(Long id);

    void updateWithFlavor(DishDTO dishDTO);
}
