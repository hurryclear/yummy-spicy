package com.yummy.service;

import com.yummy.dto.ShoppingCartDTO;
import com.yummy.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();
}
