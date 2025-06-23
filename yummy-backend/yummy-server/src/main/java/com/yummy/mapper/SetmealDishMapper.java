package com.yummy.mapper;

import com.yummy.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * get setmeals by dish ids
     * @param ids
     * @return
     */
    List<Long> getSetmealsByIds(List<Long> ids);
}
