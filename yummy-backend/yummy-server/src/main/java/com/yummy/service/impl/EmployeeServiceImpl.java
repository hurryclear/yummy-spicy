package com.yummy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yummy.constant.MessageConstant;
import com.yummy.constant.PasswordConstant;
import com.yummy.constant.StatusConstant;
import com.yummy.context.BaseContext;
import com.yummy.dto.EmployeeDTO;
import com.yummy.dto.EmployeeLoginDTO;
import com.yummy.dto.EmployeePageQueryDTO;
import com.yummy.entity.Employee;
import com.yummy.exception.AccountLockedException;
import com.yummy.exception.AccountNotFoundException;
import com.yummy.exception.PasswordErrorException;
import com.yummy.mapper.EmployeeMapper;
import com.yummy.result.PageResult;
import com.yummy.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * employee page query
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        // select * from employee limit 0,10

        /* PageHelper.startPage():
            protected static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal();
            protected static void setLocalPage(Page page) {
                LOCAL_PAGE.set(page);
            }
         */
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    @Override
    public void changeEmployeeStatus(Integer status, Long id) {
        // update employee set status = ? where id = ?
        // instead of passing only status and id we can pass Employee
//        employeeMapper.update(status, id);

        // we need a new employee: 2 methods
        // 1. traditional
//        Employee employee = new Employee();
//        employee.setStatus(status);
//        employee.setId(id);

        // 2. Builder ==> easier
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();

        employeeMapper.update(employee);
    }
}
