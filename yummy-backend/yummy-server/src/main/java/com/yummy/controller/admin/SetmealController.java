package com.yummy.controller.admin;

import com.yummy.dto.SetmealDTO;
import com.yummy.result.Result;
import com.yummy.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
