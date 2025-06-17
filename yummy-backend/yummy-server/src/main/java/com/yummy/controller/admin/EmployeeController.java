package com.yummy.controller.admin;

import com.yummy.constant.JwtClaimsConstant;
import com.yummy.dto.EmployeeDTO;
import com.yummy.dto.EmployeeLoginDTO;
import com.yummy.dto.EmployeePageQueryDTO;
import com.yummy.entity.Employee;
import com.yummy.properties.JwtProperties;
import com.yummy.result.PageResult;
import com.yummy.result.Result;
import com.yummy.service.EmployeeService;
import com.yummy.utils.JwtUtil;
import com.yummy.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Employee management
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * login
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("Employee login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        /*
            generate jwt-token after successful login
            1.
         */
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        /*
            1. what is VO?
            2. what and how to use .builder()?
         */

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * logout
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add new employee
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new employee")
    // 数据格式如果是json，需要加requestbody注解， 如果数据格式是Query，直接声明参数，springmvc框架会把数据封装成dto对象
    // requestbody because this is PostMapping, and no requestbody because just GetMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("add new employee: {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Employee page query")
    public Result<PageResult> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("employee page query: {}", employeePageQueryDTO);
        // #TODO: the pageNumber by default is 0, it doesn't work!!
//        employeePageQueryDTO.setPageNumber(1);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        // Result: code, msg, data
        return Result.success(pageResult);
    }

    /**
     * change employees' status
     * status will be passed through the path
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("change employee status")
    public Result changeEmployeeStatus(@PathVariable Integer status, Long id) {
        employeeService.changeEmployeeStatus(status, id);
        return Result.success();
    }
}
