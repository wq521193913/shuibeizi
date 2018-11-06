package com.shuibeizi.sys.service;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 
 * @author Administrator
 * @date 2018-10-29 21:36:26
*/
public interface ITemplateServiceWrite {

    /**
     * 新增
     * @param sysUser
     * @return
     * @throws CustomException
     */
    void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException;

    /**
     * 根据id修改
     * @param sysUser
     * @return
     * @throws CustomException
     */
    int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException;

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteTemplate(Integer id, Consumer<String> errorCall) throws CustomException;

}
