package com.yummy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yummy.constant.MessageConstant;
import com.yummy.constant.StatusConstant;
import com.yummy.dto.SetmealDTO;
import com.yummy.dto.SetmealPageQueryDTO;
import com.yummy.entity.Setmeal;
import com.yummy.entity.SetmealDish;
import com.yummy.exception.DeletionNotAllowedException;
import com.yummy.mapper.SetmealDishMapper;
import com.yummy.mapper.SetmealMapper;
import com.yummy.result.PageResult;
import com.yummy.service.SetmealService;
import com.yummy.vo.SetmealVO;
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
}
