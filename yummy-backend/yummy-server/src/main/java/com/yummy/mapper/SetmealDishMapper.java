package com.yummy.mapper;

import com.yummy.annotation.AutoFill;
import com.yummy.entity.Setmeal;
import com.yummy.entity.SetmealDish;
import com.yummy.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
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

    void insert(List<SetmealDish> setmealDishes);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
}
