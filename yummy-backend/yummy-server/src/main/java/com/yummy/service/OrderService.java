package com.yummy.service;

import com.yummy.dto.OrdersPageQueryDTO;
import com.yummy.dto.OrdersSubmitDTO;
import com.yummy.result.PageResult;
import com.yummy.vo.OrderSubmitVO;
import com.yummy.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO details(Long id);
}
