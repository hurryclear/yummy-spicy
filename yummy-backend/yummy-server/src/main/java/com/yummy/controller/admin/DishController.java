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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dish api")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

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

}
