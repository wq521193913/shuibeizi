package com.shuibeizi.common.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 获取配置文件属性
 * @author:wanqing
 * @date:2017/1/2 14:20
 */
public class PropertiesUtils {

    /**
     * 获取配置属性
     * @param key
     * @return
     */
    public static String getProperties(String key){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/configuration");
        if(null == resourceBundle) return null;
        return resourceBundle.getString(key);
    }

    /**
     * 获取资源配置属性
     * @param key
     * @return
     */
    public static String getSourceProperties(String key){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/source/uploadField");
        if(null == resourceBundle) return null;
        return resourceBundle.getString(key);
    }

    /**
     * 得到角色对应的APP模块
     * @param key
     * @return
     */
    public static String getAppModuleSource(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/appmodule");
        if(null == resourceBundle) return null;
        return resourceBundle.getString(key);
    }

    /**
     * 得到设置的角色
     * @return
     */
    public static Enumeration<String> getAppRole(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/appmodule");
        if(null == resourceBundle) return null;
        return resourceBundle.getKeys();
    }
}
