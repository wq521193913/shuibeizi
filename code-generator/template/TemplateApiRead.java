package com.shuibeizi.sys.read.producer;

import com.shuibeizi.sys.api.ISysUserApi;
import com.shuibeizi.sys.entity.SysUser;
import com.shuibeizi.sys.service.ISysUserServiceRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
public class TemplateApiRead implements ITemplateApi {

    @Autowired
    ITemplateServiceRead templateServiceRead;

    @Override
    public Template queryTemplateById(@RequestBody Template template) {
        return templateServiceRead.queryTemplateById(sysUser);
    }

    @Override
    public List<Template> queryTemplateList(Map<String, Object> map) {
        return templateServiceRead.queryTemplateList(map);
    }
}
