package com.yummy.controller.admin;

import com.yummy.constant.MessageConstant;
import com.yummy.dto.DishDTO;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.entity.Dish;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.DishService;
import com.yummy.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dish api")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * add new dish with flavors
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new dishes")
    public Result addWithFlavors(@RequestBody DishDTO dishDTO) {
        log.info("Add new dishes: {}", dishDTO);
        dishService.addWithFlavors(dishDTO);

        // clear cache data
        String key = "dish_" + dishDTO.getCategoryId();
        clearCache(key);
        return Result.success();
    }

    /**
     * dish page query
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Dishes page query")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Dish page query: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * delete dishes by ids
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete dishes")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Delete dishes: {}", ids);
        dishService.deleteByIds(ids);
        // delete all key with "dish_"
        // 1. find all keys opening with "dish_"
        Set keys = redisTemplate.keys("dish_*");
        // 2. delete all keys (collection)
        redisTemplate.delete(keys);

        return Result.success();
    }

    /**
     * get dish by id
     * when you want to edit/update dishes, you click one dish, it will show the current info of
     * dish
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("get dish by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("get dish by id: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavors(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("update dish")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("update dish: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        // delete all keys with "dish_"
        clearCache("dish_*");
        return Result.success();
    }
//    @GetMapping("/list/{categoryId}")
    // #TODO how to pass category id from frontend to backend?
    @GetMapping("/list")
    @ApiOperation("list dishes by category id")
    public Result<List<Dish>> listByCategoryId(Long categoryId) {
         log.info("list dishes by category id: {}", categoryId);
         List<Dish> dishList = dishService.listByCategoryId(categoryId);
         return Result.success(dishList);
    }

    // #TODO update status of dish
    @PutMapping("/status/{stauts}")
    public Result<String> updateStatus(@PathVariable Integer stauts, Long id) {
        // change status
        // delete all keys with "dish_"
        clearCache("dish_*");
        return Result.success();
    }


    // method for clearing cache data
    private void clearCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
