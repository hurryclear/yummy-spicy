package com.yummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //wechat user unique id 微信用户唯一标识
    private String openid;

    //username
    private String name;

    //phone number
    private String phone;

    //gender: 0-female, 1-male
    private String sex;

    //id number
    private String idNumber;

    //avatar url
    private String avatar;

    //register time
    private LocalDateTime createTime;
}
