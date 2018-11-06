package com.shuibeizi.sys.dao;

import com.shuibeizi.sys.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2018-10-29 21:53:51
 */
@Repository
public interface SysUserDao {

    /**
     * 新增
     * @param sysUser
     * @return
     */
    void insertSysUser(SysUser sysUser);

    /**
     * 根据id修改
     * @param sysUser
     * @return
     */
    int updateSysUserById(SysUser sysUser);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteSysUser(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return SysUser
     */
    SysUser querySysUserById(Integer id);

    /**
     * 查询列表
     * @param map
     * @return List<SysUser>
     */
    List<SysUser> querySysUserList(Map<String, Object> map);

    /**
     * 查询分页列表
     * @param map
     * @return List<SysUser>
     */
    List<SysUser> querySysUserPageList(Map<String, Object> map);

    /**
     * 查询分页列表条数
     * @param map
     * @return Integer
     */
    Integer querySysUserPageCount(Map<String, Object> map);
}
