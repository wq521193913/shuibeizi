package com.shuibeizi.admin.controller;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.common.util.ApiResult;
import com.shuibeizi.common.util.Result;
import com.shuibeizi.sys.api.ISysUserApiRead;
import com.shuibeizi.sys.api.ISysUserApiWrite;
import com.shuibeizi.sys.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2018-11-03 10:58:41
*/
@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController {

	private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private ISysUserApiRead sysUserApiRead;
    @Resource
    private ISysUserApiWrite sysUserApiWrite;

    /**
     * 新增
     * @param sysUser
     * @return
     * @author Administrator
     * @date 2018-11-03 10:58:41
    */
    @RequestMapping(value = "insertSysUser", method = RequestMethod.POST)
    @ResponseBody
    public Result insertSysUser(@RequestBody SysUser sysUser){
        Result result = new Result();
        try {
            ApiResult apiResult = sysUserApiWrite.insertSysUser(sysUser);
            result = Result.getForApiResult(apiResult);
        }catch (CustomException ce){
            logger.error("params:" + sysUser, ce);
            return Result.getSystemErrorMsg(ce);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("SysUserController.insertSysUser error:", e);
        }
        return result;
    }

    /**
     * 根据id更新表数据
     * @param sysUser
     * @return
     * @author Administrator
     * @date 2018-11-03 10:58:41
    */
    @RequestMapping(value = "updateSysUserById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSysUserById(@RequestBody SysUser sysUser){
        Result result = new Result();
        try {
            sysUserApiWrite.updateSysUserById(sysUser);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("SysUserController.updateSysUserById error:", e);
        }
        return result;
    }

    /**
     * 删除表数据
     * @param id
     * @return
     * @author Administrator
     * @date 2018-11-03 10:58:41
    */
    @RequestMapping(value = "deleteSysUser", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSysUser(@RequestParam(value = "id")Integer id){
        Result result = new Result();
        try {
            sysUserApiWrite.deleteSysUser(id);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("SysUserController.deleteSysUser error:", e);
        }
        return result;
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     * @author Administrator
     * @date 2018-11-03 10:58:41
    */
    @RequestMapping(value = "querySysUserById", method = RequestMethod.GET)
    @ResponseBody
    public Result querySysUserById(@RequestParam(value = "id")Integer id){
        Result result = new Result();
        try {
            SysUser sysUser = sysUserApiRead.querySysUserById(id);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("SysUserController.querySysUserById error:", e);
        }
        return result;
    }

    /**
     * 查询列表
     * @param
     * @return
     * @author Administrator
     * @date 2018-11-03 10:58:41
    */
    @RequestMapping(value = "querySysUserList", method = RequestMethod.GET)
    @ResponseBody
    public Result querySysUserList(ServletRequest request){
        Result result = new Result();
        try {
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request,"");
            List<SysUser> sysUserList = sysUserApiRead.querySysUserList(paramMap);
            result.setData(sysUserList);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("SysUserController.querySysUserList error:", e);
        }
        return result;
    }
}
