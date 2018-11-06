package com.shuibeizi.sys.service;

import com.shuibeizi.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 * @date 2018-10-29 21:36:26
*/
public interface ITemplateServiceRead {

    /**
     * 根据id查询
     * @param sysUser
     * @return
     */
    Template queryTemplateById(Template template);

    /**
     * 查询列表
     * @param map
     * @return
     */
    List<Template> queryTemplateList(Map<String, Object> map);

}
