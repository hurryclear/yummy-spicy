package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * employee login
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        // username and password from the frontend
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1. query database according to username
        // return the employee that get from database
        Employee employee = employeeMapper.getByUsername(username);

        // 2. exceptions handler: no username, wrong password, account locked
        if (employee == null) {
            // account not found
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // compare password
        // MD5: MD5 Message-Digest Algorithm
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            // password incorrect
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // status: 1 locked, 0 unlocked
        if (employee.getStatus() == StatusConstant.DISABLE) {
            // account locked
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 3. return entity object
        return employee;
    }

    /**
     * add new employee
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        // object property copy
        BeanUtils.copyProperties(employeeDTO, employee);
        // set the rest properties
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // #TODO: id of creator and editor
        // also want to save the id of the creator/editor of the new employee ==> who add/edit
        // the new employee? ==> how can you get the id? ==> ThreadLocal
        // BaseContext encapsulate the ThreadLocal
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

}
