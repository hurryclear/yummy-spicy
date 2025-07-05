package com.yummy.service;

import com.yummy.dto.UserLoginDTO;
import com.yummy.entity.User;

public interface UserService {
    User wcLogin(UserLoginDTO userLoginDTO);
}
