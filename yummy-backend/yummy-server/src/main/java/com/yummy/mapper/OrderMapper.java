package com.yummy.mapper;

import com.github.pagehelper.Page;
import com.yummy.dto.OrdersPageQueryDTO;
import com.yummy.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
}
