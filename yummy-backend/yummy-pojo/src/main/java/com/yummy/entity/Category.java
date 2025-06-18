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
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // type: 1-dish, 2-set
    private Integer type;

    // category name
    private String name;

    // sort
    private Integer sort;

    // category status: 0-disable, 1-enable
    private Integer status;

    // create time
    private LocalDateTime createTime;

    // update time
    private LocalDateTime updateTime;

    // create user
    private Long createUser;

    // update user
    private Long updateUser;
}
