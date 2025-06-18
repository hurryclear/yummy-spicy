package com.yummy.controller.admin;

import com.yummy.dto.CategoryDTO;
import com.yummy.dto.CategoryPageQueryDTO;
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
}
