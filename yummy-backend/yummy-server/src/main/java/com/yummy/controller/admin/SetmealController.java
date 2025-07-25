package com.yummy.controller.admin;

import com.yummy.dto.SetmealDTO;
import com.yummy.dto.SetmealPageQueryDTO;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.SetmealService;
import com.yummy.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
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

    /**
     * delete setmeals or setmeal
     * can be used to delete one setmeal or many setmeals at once, depends on ids
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete meal-set")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    // #TODO when to use @RequestParam?
    public Result delete(@RequestParam List<Long> ids) {
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    // 用来实现修改时的内容回显
    @GetMapping("/{id}")
    @ApiOperation("get setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * update setmeal
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update setmeal")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * change status of setmeal through id and path variable "status"
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("change status of setmeal")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result changeStatus(@PathVariable Integer status, Long id) {
        setmealService.changeStatus(status, id);
        return Result.success();
    }
}
