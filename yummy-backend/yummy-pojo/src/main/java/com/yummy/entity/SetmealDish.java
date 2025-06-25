package com.yummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // setmeal id
    private Long setmealId;

    //dish id
    private Long dishId;

    // dish name （冗余字段）
    private String name;

    // dish price or set meal price?
    private BigDecimal price;

    // how many?
    private Integer copies;
}
