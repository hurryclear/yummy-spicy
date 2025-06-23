package com.yummy.mapper;

import com.yummy.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealsByIds(List<Long> ids);
}
