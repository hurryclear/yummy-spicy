package com.yummy.mapper;

import com.yummy.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    // list dish (with flavor), setmeal by user_id
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    // update
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart(name, user_id, dish_id, setmeal_id, dish_flavor, number, " +
            "amount, image, create_time)"
            + "values (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, " +
            "#{amount}, #{image}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    @Select("select from shopping_cart where user_id = #{userId}")
    ShoppingCart getByUserId(Long userId);
}
