package com.yummy.mapper;

import com.yummy.annotation.AutoFill;
import com.yummy.entity.Setmeal;
import com.yummy.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface SetmealMapper {
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);
}
