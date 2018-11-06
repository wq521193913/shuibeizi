package com.shuibeizi.admin.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shuibeizi.common.util.Result;
import com.shuibeizi.sys.api.ISysUserApiRead;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/25 0025 10:33
 * @modified:
 */
@Controller
public class MainController {

    @Autowired
    private ISysUserApiRead sysUserApiRead;

//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "20"),
//            @HystrixProperty(name = "execution.timeout.enabled", value = "true")
//    },fallbackMethod = "querySysUserByIdFall")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result test(@RequestParam(value = "id",required = false)Integer id){
        Result result = new Result();
        SysUser sysUser = sysUserApiRead.querySysUserById(id);
        result.setData(sysUser);
        return result;
    }

    public Result querySysUserByIdFall(Integer id){
        Result result = new Result();
        result.setData(new SysUser().setUid(0).setUserName("fallback user").setRemarks("fallback remark"));
        return result ;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Result test1(){
        Result result = new Result();
        return result;
    }
}
