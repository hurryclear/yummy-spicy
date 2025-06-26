package com.yummy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yummy.constant.MessageConstant;
import com.yummy.constant.StatusConstant;
import com.yummy.dto.SetmealDTO;
import com.yummy.dto.SetmealPageQueryDTO;
import com.yummy.entity.Dish;
import com.yummy.entity.Setmeal;
import com.yummy.entity.SetmealDish;
import com.yummy.exception.DeletionNotAllowedException;
import com.yummy.exception.SetmealEnableFailedException;
import com.yummy.mapper.DishMapper;
import com.yummy.mapper.SetmealDishMapper;
import com.yummy.mapper.SetmealMapper;
import com.yummy.result.PageResult;
import com.yummy.service.SetmealService;
import com.yummy.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

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
        Long setmealId = setmeal.getId();

        // add relationship between set meal and dishes into the "setmeal_dish" table
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishMapper.insert(setmealDishes);

    }

    /**
     * setmeal page query
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * delete setmeals by ids
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        // 1. can't delete if setmeal on sale (need to get the status first)
//        for (Long id : ids) {
//            Setmeal setmeal = setmealMapper.getById(id);
//            if (setmeal.getStatus() == StatusConstant.ENABLE) {
//                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
//            }
//        }
        // write more lambda
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        // 2. delete with for-loop
        ids.forEach(setmealId -> {
            // delete setmeal from "setmeal" table
            setmealMapper.deleteById(setmealId);
            // delete setmeal from "setmeal_dish" table
            setmealDishMapper.deleteBySetmealId(setmealId);
        });
        // setmealMapper.delete(id) or setmealMapper.deleteBatch(ids) ?
    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(id);
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishList);
        return setmealVO;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        // 如何实现回显？ get by id ==> backend provide get by id api and frontend will use it to do
        // "setmeal" table
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        // "setmeal_dish" table
        Long setmealId = setmealDTO.getId();
        // 1. delete the old
        setmealDishMapper.deleteBySetmealId(setmealId);
        // 2. insert the new
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        setmealDishList.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setmealDishMapper.insert(setmealDishList);

    }

    /**
     * change status of setmeal
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Long id) {
        /* business rules
           1. if the setmeal is on sale you can disable it
           2. if the setmeal is not on sale you can enable it and you have to ensure that all
           dishes within this setmeal are on sale, otherwise you can't enable this setmeal
           3. customer can only see the setmeals which are on sale
         */
        if(status == StatusConstant.ENABLE) {
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && dishList.size() > 0) {
                dishList.forEach(dish -> {
                    if(dish.getStatus() == StatusConstant.DISABLE) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }
}
