package com.yummy.controller.user;

import com.yummy.constant.StatusConstant;
import com.yummy.entity.Setmeal;
import com.yummy.result.Result;
import com.yummy.service.SetmealService;
import com.yummy.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "Client-setmeal browser api")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * search setmeal by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("search setmeal by category id")
    public Result<List<Setmeal>> list(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * search dishes of a setmeal by setmeal category id
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("search dishes of a setmeal by setmeal category id")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
