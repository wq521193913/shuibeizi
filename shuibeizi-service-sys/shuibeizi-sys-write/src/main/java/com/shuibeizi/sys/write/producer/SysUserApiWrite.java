package com.shuibeizi.sys.write.producer;

import com.shuibeizi.common.enumerate.ResultCode;
import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.common.util.ApiResult;
import com.shuibeizi.sys.api.ISysUserApiWrite;
import com.shuibeizi.sys.entity.SysUser;
import com.shuibeizi.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/3 0003 10:42
 * @modified:
 */
@RestController
public class SysUserApiWrite implements ISysUserApiWrite {

    @Autowired
    ISysUserService sysUserServiceWrite;

    @Override
    public ApiResult insertSysUser(@RequestBody SysUser sysUser) throws CustomException {
        ApiResult apiResult = new ApiResult();
        sysUserServiceWrite.insertSysUser(sysUser,error->{
            apiResult.setCode(ResultCode.FAIL.getCode())
                    .setMsg(error);
        });
        return apiResult;
    }

    @Override
    public ApiResult<Integer> updateSysUserById(@RequestBody SysUser sysUser) throws CustomException {
        ApiResult apiResult = new ApiResult();
        sysUserServiceWrite.updateSysUserById(sysUser, error->{
            apiResult.setCode(ResultCode.FAIL.getCode())
                    .setMsg(error);
        });
        return apiResult;
    }

    @Override
    public ApiResult<Integer> deleteSysUser(@RequestParam(value = "id") Integer id) throws CustomException {
        ApiResult apiResult = new ApiResult();
        sysUserServiceWrite.deleteSysUser(id, error->{
            apiResult.setCode(ResultCode.FAIL.getCode())
                    .setMsg(error);
        });
        return apiResult;
    }
}
