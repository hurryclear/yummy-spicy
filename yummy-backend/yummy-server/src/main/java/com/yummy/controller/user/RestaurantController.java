package com.yummy.controller.user;

import com.yummy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user restaurant status
 */
@RestController("userRestaurantController")
@RequestMapping("/user/shop")
@Api(tags = "Restaurant status")
@Slf4j
public class RestaurantController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * user: get restaurant status
     * @return
     */
    @GetMapping("/staus")
    @ApiOperation("get restaurant status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("get restaurant status: {}", status == 1 ? "in business" : "in break");
        return Result.success(status);
    }

}
