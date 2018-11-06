package com.shuibeizi.sys.api;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.common.util.ApiResult;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/2 0002 09:53
 * @modified:
 */
@FeignClient(name = "SHUIBEIZI-SYS-WRITE")
public interface ISysUserApiWrite {

//    static final String SERVICE_NAME = "SHUIBEIZI-SYS";

    @RequestMapping(value ="insertSysUser")
    ApiResult insertSysUser(@RequestBody SysUser sysUser) throws CustomException;

    @RequestMapping(value ="updateSysUserById")
    ApiResult<Integer> updateSysUserById(@RequestBody SysUser sysUser) throws CustomException;

    @RequestMapping(value ="deleteSysUser")
    ApiResult<Integer> deleteSysUser(@RequestParam(value = "id") Integer id) throws CustomException;

}
