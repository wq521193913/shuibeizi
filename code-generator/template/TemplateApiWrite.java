package com.shuibeizi.sys.write.producer;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.sys.api.ISysUserApi;
import com.shuibeizi.sys.entity.SysUser;
import com.shuibeizi.sys.service.ISysUserServiceWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/3 0003 10:42
 * @modified:
 */
@RestController
public class TemplateApiWrite implements ITemplateApi {

    @Autowired
    ITemplateServiceWrite templateServiceWrite;

    @Override
    public void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException {
        templateServiceWrite.insertTemplate(template, errorCall);
    }

    @Override
    public int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException {
        return templateServiceWrite.updateTemplateById(template, errorCall);
    }

    @Override
    public int deleteTemplate(Integer id, Consumer<String> errorCall) throws CustomException {
        return templateServiceWrite.deleteTemplate(id, errorCall);
    }
}
