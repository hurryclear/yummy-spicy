package com.yummy.controller.user;

import com.yummy.constant.JwtClaimsConstant;
import com.yummy.dto.UserLoginDTO;
import com.yummy.entity.User;
import com.yummy.properties.JwtProperties;
import com.yummy.result.Result;
import com.yummy.service.UserService;
import com.yummy.utils.JwtUtil;
import com.yummy.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "user api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("wechat login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("wechat user login: {}", userLoginDTO.getCode());
        // wechat login
        User user = userService.wcLogin(userLoginDTO);
        // create jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String jwtToken = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        // create userLoginVO
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(jwtToken)
                .build();
        return Result.success(userLoginVO);
    }
}
