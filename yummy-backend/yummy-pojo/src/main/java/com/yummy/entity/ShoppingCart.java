package com.yummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * shopping cart entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //name
    private String name;

    //user id
    private Long userId;

    //dish id
    private Long dishId;

    //set meal id
    private Long setmealId;

    //flavor
    private String dishFlavor;

    //number
    private Integer number;

    //amount
    private BigDecimal amount;

    //image
    private String image;

    private LocalDateTime createTime;
}
