package com.shuibeizi.sys.read.impl;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.sys.service.ISysUserService;
import com.shuibeizi.sys.dao.SysUserDao;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/30 0030 21:35
 * @modified:
 */
@Service
public class SysUserServiceRead implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser querySysUserById(Integer id) {
        if(null == id) throw new CustomException("参数检验有误");
        SysUser sysUser1 = sysUserDao.querySysUserById(id);
        return sysUser1;
    }

    @Override
    public List<SysUser> querySysUserList(Map<String, Object> map) {
        return sysUserDao.querySysUserList(map);
    }
}
