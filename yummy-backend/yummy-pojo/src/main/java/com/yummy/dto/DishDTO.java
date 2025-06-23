package com.yummy.dto;

import com.yummy.entity.DishFlavor;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {

    private Long id;
    // dish name
    private String name;
    //category id
    private Long categoryId;
    // dish price
    private BigDecimal price;
    // image
    private String image;
    // description
    private String description;
    // 0 not on sale, 1 on sale
    private Integer status;
    // flavors
    private List<DishFlavor> flavors = new ArrayList<>();

}
