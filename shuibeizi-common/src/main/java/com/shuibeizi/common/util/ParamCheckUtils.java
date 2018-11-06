package com.shuibeizi.common.util;

import com.shuibeizi.common.exception.CustomException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 参数检查
 * @author:wanqing
 * @date:2016/11/23 10:48
 */
public class ParamCheckUtils {

    private ParamCheckUtils(){}

    private static class InstanceObj{
        private static final ParamCheckUtils instance = new ParamCheckUtils();
    }

    public static ParamCheckUtils getInstance(){
        return InstanceObj.instance;
    }

    /**
     *
     * @param entity
     * @param requiredFields Map(code,name)
     * @return
     * @author: wanqing
     * @date: 2016/12/9 10:20
     */
    public boolean paramIsRequired(Object entity, Map<String, String> requiredFields) throws CustomException, IllegalAccessException {
        Class entityClass;
        Field field = null;
        String failMsg = "";
        for (String requiredField : requiredFields.keySet()){
            entityClass = entity.getClass();
            for (;entityClass != Object.class; entityClass = entityClass.getSuperclass()){
                try {
                    field = entityClass.getDeclaredField(requiredField);
                } catch (Exception e) {
                    continue;
                }
            }
            if(null != field){
                field.setAccessible(true);
            }
            if(null == field || (null != field.get(entity) && "".equals(field.get(entity))) || null == field.get(entity)){
                failMsg += requiredFields.get(requiredField) + ",";

            }
        }
        if(!failMsg.equals("")){
            failMsg = failMsg.substring(0, failMsg.length() - 1);
            throw new CustomException("以下参数不可为空:"+failMsg+"");
        }
        return true;
    }

    /**
     *
     * @param sourceMap
     * @param requiredFields Map(code,name)
     * @return
     * @author: Administrator
     * @date: 2016/12/9 10:24
     */
    public boolean paramIsRequired(Map<String, Object> sourceMap, Map<String, String> requiredFields) throws CustomException, IllegalAccessException {
        String errMsg = "";
        for (String requiredField : requiredFields.keySet()){
            if(sourceMap.containsKey(requiredField) && null != sourceMap.get(requiredField)){
                continue;
            }else {
                errMsg += requiredFields.get(requiredField) + ",";
            }
        }
        if(!"".equals(errMsg)){
            errMsg = errMsg.substring(0, errMsg.length() - 1);
            throw new CustomException("以下参数:" + errMsg + ",检验有误");
        }
        return true;
    }
}
