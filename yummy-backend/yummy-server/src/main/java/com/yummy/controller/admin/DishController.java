package com.yummy.controller.admin;

import com.yummy.dto.DishDTO;
import com.yummy.result.Result;
import com.yummy.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Api")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("Add new dishes")
    public Result<String> add(DishDTO dishDTO) {
        log.info("Add new dishes: {}", dishDTO);
        dishService.add(dishDTO);
        return Result.success();
    }
}
