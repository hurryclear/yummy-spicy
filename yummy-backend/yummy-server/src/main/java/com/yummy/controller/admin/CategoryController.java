package com.yummy.controller.admin;

import com.yummy.dto.CategoryDTO;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.entity.Category;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Add new category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new category")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("Add new category: {}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * Category page query
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Category page query")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("Category page query: {}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Update category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update/Edit category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("Update/edit category: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * Change status of category
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Change status of category")
    public Result<String> changeStatus(@PathVariable Integer status, Long id) {
        // #TODO: how is "id" passed from frontend?
        log.info("Change status: {}", status + id);
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    /**
     * Delete by id
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete by id")
    public Result<String> deleteById(Long id) {
        // how is "id" passed from frontend?
        categoryService.deleteById(id);
        return Result.success();
    }
}

