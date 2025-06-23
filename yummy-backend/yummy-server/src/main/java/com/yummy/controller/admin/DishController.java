package com.yummy.controller.admin;

import com.yummy.dto.DishDTO;
import com.yummy.dto.DishPageQueryDTO;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dish api")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * add new dish with flavors
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
}
