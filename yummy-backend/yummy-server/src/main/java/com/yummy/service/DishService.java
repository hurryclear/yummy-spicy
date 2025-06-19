package com.yummy.service;

import com.yummy.dto.DishDTO;
import org.springframework.stereotype.Service;

public interface DishService {
    void add(DishDTO dishDTO);
}
