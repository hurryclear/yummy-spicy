package com.yummy.mapper;

import com.github.pagehelper.Page;
import com.yummy.annotation.AutoFill;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.entity.Dish;
import com.yummy.enumeration.OperationType;
import com.yummy.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

//    @Select("select * from dish where category_id = #{categoryId}")
//    List<Dish> getByCategoryId(Long categoryId); // this is not correct!
//    Because you need also be able to search with the name of dish, if you only pass categoryId
//    to mapper, how can database search dish by the name?

    List<Dish> list(Dish dish);


    @Select("select d.* from dish d left join setmeal_dish sd on d.id = sd.dish_id where sd" +
            ".setmeal_id = #{id}")
    List<Dish> getBySetmealId(Long id);
}
