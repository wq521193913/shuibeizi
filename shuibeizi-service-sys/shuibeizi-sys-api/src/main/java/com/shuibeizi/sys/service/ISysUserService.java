package com.shuibeizi.sys.service;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 
 * @author Administrator
 * @date 2018-10-29 21:36:26
*/
public interface ISysUserService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    default SysUser querySysUserById(Integer id){
        return null;
    }

    /**
     * 查询列表
     * @param map
     * @return
     */
    default List<SysUser> querySysUserList(Map<String, Object> map){
        return null;
    }

    /**
     * 新增
     * @param sysUser
     * @return
     * @throws CustomException
     */
    default void insertSysUser(SysUser sysUser, Consumer<String> errorCall) throws CustomException{
        return;
    }

    /**
     * 根据id修改
     * @param sysUser
     * @return
     * @throws CustomException
     */
    default int updateSysUserById(SysUser sysUser, Consumer<String> errorCall) throws CustomException{
        return 0;
    };

    /**
     * 删除
     * @param id
     * @return
     */
    default int deleteSysUser(int id, Consumer<String> errorCall) throws CustomException{
        return 0;
    }

}
