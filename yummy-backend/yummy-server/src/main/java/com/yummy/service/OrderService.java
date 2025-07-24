package com.yummy.service;

import com.yummy.dto.OrdersSubmitDTO;
import com.yummy.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
