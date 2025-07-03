package com.yummy.controller.admin;

import com.yummy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * admin restaurant status
 */

@RestController("adminRestaurantController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "Restaurant status")
public class RestaurantController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String KEY = "SHOP_STATUS";

    /**
     * admin: set restaurant status
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("set restaurant status")
    public Result setStatus(@PathVariable Integer status) {
        log.info("set restaurant status: {}", status == 1 ? "in business" : "in break");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * admin: get restaurant status
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("get restaurant status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("get restaurant status: {}", status == 1 ? "in business" : "in break");
        return Result.success(status);
    }
}
