package com.shuibeizi.sys.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/2 0002 09:53
 * @modified:
 */
@FeignClient(name = "SHUIBEIZI-SYS-READ")
public interface ISysUserApiRead {

//    static final String SERVICE_NAME = "SHUIBEIZI-SYS";

    @RequestMapping(value ="querySysUserById")
    SysUser querySysUserById(@RequestParam(value = "id") Integer id);

    @RequestMapping(value ="querySysUserList")
    List<SysUser> querySysUserList(@RequestBody Map<String, Object> map);

}
