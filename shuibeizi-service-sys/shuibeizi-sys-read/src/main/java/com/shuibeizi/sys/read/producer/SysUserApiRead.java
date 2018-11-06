package com.shuibeizi.sys.read.producer;

import com.shuibeizi.sys.api.ISysUserApiRead;
import com.shuibeizi.sys.entity.SysUser;
import com.shuibeizi.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/2 0002 10:03
 * @modified:
 */
@RestController
public class SysUserApiRead implements ISysUserApiRead {

    @Autowired
    ISysUserService sysUserServiceRead;

    @Override
    public SysUser querySysUserById(@RequestParam(value = "id") Integer id) {
//        try {
//            Thread.sleep(1000*60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return sysUserServiceRead.querySysUserById(id);
    }


    @Override
    public List<SysUser> querySysUserList(@RequestBody Map<String, Object> map) {
        return sysUserServiceRead.querySysUserList(map);
    }

}
