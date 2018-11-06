package com.shuibeizi.sys.write.impl;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.common.util.ParamsValidator;
import com.shuibeizi.sys.dao.SysUserDao;
import com.shuibeizi.sys.entity.SysUser;
import com.shuibeizi.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/30 0030 13:48
 * @modified:
 */
@Service
public class SysUserServiceWrite implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void insertSysUser(SysUser sysUser, Consumer<String> errorCall) throws CustomException {
        ParamsValidator.getInstance().getValidator(sysUser,null);
        sysUserDao.insertSysUser(sysUser);
    }

    @Override
    public int updateSysUserById(SysUser sysUser, Consumer<String> errorCall) throws CustomException {
        int affectRow = 0;
        if(null != sysUser && null != sysUser.getUid()){
            SysUser userExit = sysUserDao.querySysUserById(sysUser.getUid());
            if(null == userExit) {
                errorCall.accept("无法查询到此数据");
                return 0;
            }
            affectRow = sysUserDao.updateSysUserById(sysUser);
        }else {
            errorCall.accept("参数检验有误:用户id不能为空");
        }
        return affectRow;
    }

    @Override
    public int deleteSysUser(int id, Consumer<String> errorCall) throws CustomException {
        int affectRow = sysUserDao.deleteSysUser(id);
        return affectRow;
    }
}
