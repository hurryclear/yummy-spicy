package com.yummy.controller.admin;

import com.yummy.dto.SetmealDTO;
import com.yummy.dto.SetmealPageQueryDTO;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "setmeal api")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * add new set meal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("add new set meal")
    public Result add (@RequestBody SetmealDTO setmealDTO) {
        log.info("Add new set meal: {}", setmealDTO);
        setmealService.add(setmealDTO);
        return Result.success();
    }

    /**
     * page query
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("setmeal page query")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
