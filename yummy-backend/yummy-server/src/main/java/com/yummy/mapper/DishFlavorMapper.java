package com.yummy.mapper;

import com.yummy.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> flavors);


    /**
     * delete dish_flavor by dish id
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);
}
