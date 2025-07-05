package com.yummy.mapper;

import com.yummy.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * User data access layer
 * Handles database operations for the user entity
 */
@Mapper
public interface UserMapper {

    /**
     * Find user by WeChat openid
     * @param openid WeChat user unique identifier
     * @return User entity or null if not found
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * Insert new user into database
     * @param user User entity to insert
     */
    void insert(User user);
}