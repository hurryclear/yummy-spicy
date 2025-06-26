package com.yummy.service;

import com.yummy.dto.SetmealDTO;
import com.yummy.dto.SetmealPageQueryDTO;
import com.yummy.result.PageResult;
import com.yummy.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void add(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBatch(List<Long> ids);
    SetmealVO getByIdWithDish(Long id);
    void update(SetmealDTO setmealDTO);

    void changeStatus(Integer status, Long id);
}
