package com.yummy.controller.user;

import com.yummy.constant.StatusConstant;
import com.yummy.entity.Dish;
import com.yummy.result.Result;
import com.yummy.service.DishService;
import com.yummy.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "Client-Dish browser api")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * search dishes by category id
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("search dish by category id")
    public Result<List<DishVO>> list(Long categoryId) {

        // redis key: "dish_" + categoryId
        String key = "dish_" + categoryId;
        // Does dish data exist in redis?
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 1. yes, exist
        if (list != null && list.size() > 0) {
            return Result.success(list);
        }
        // 2. no, doesn't exist
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);// search which is on sale

        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
