package com.yummy.service.impl;

import com.yummy.dto.SetmealDTO;
import com.yummy.entity.Setmeal;
import com.yummy.entity.SetmealDish;
import com.yummy.mapper.SetmealDishMapper;
import com.yummy.mapper.SetmealMapper;
import com.yummy.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * add new set meal
     * @param setmealDTO
     */
    @Override
    public void add(SetmealDTO setmealDTO) {

        // add new set meal into the "setmeal" table
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        // because we use this in mybatis: <insert id="insert" useGeneratedKeys="true"
        // keyProperty="id">
        // so the automatically-generated id will return and we can get it
        Long id = setmeal.getId();

        // add relationship between set meal and dishes into the "setmeal_dish" table
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        setmealDishMapper.insert(setmealDishes);

    }
}
