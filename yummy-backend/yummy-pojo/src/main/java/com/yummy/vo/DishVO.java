package com.yummy.vo;

import com.yummy.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishVO implements Serializable {

    private Long id;
    // dish name
    private String name;
    // dish category id 菜品分类id
    private Long categoryId;
    //dish price 菜品价格
    private BigDecimal price;
    // image 图片
    private String image;
    // description 描述信息
    private String description;
    // 0 out of business 1 on sale
    private Integer status;
    // update time
    private LocalDateTime updateTime;
    // category name 分类名称
    private String categoryName;
    // with dish bounden flavors 菜品关联的口味
    private List<DishFlavor> flavors = new ArrayList<>();

    //private Integer copies;
}
