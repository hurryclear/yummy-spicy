package com.yummy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yummy.constant.MessageConstant;
import com.yummy.dto.UserLoginDTO;
import com.yummy.entity.User;
import com.yummy.exception.LoginFailedException;
import com.yummy.mapper.UserMapper;
import com.yummy.properties.WeChatProperties;
import com.yummy.service.UserService;
import com.yummy.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // WeChat login API endpoint for code-to-session exchange
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * WeChat login implementation
     * 1. Exchange WeChat code for openid
     * 2. Check if a user exists in database
     * 3. Create a new user if not exists
     * 4. Return user entity
     */
    @Override
    public User wcLogin(UserLoginDTO userLoginDTO) {
        // 1. Get openid from WeChat API using the provided code
        String openid = getOpenid(userLoginDTO.getCode());
        
        // 2. Validate openid received from WeChat
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 3. Check if a user already exists in the database
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            // Create a new user with openid and current timestamp
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        // 4. return user
        return user;
    }

    /**
     * Call WeChat API to exchange code for openid
     * @param code WeChat login code from mini program
     * @return openid unique identifier for WeChat user
     */
    private String getOpenid(String code) {
        // Build request parameters for WeChat API
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        // Make HTTP GET request to WeChat API
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        // Parse JSON response and extract openid
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        return openid;
    }
}
