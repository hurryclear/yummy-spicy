package com.yummy.dto;

import com.yummy.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    // category id
    private Long categoryId;

    // set meal name
    private String name;

    // set meal price
    private BigDecimal price;

    //status 0:disable 1:enable
    private Integer status;

    // description
    private String description;

    // image
    private String image;

    // relationship between set meal and dishes
    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
