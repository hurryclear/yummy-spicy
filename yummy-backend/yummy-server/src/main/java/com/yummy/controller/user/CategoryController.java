package com.yummy.controller.user;

import com.yummy.entity.Category;
import com.yummy.result.Result;
import com.yummy.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "Client-Classification Api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * search catetory
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Search category")
    public Result<List<Category>> list(Integer type) {
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
