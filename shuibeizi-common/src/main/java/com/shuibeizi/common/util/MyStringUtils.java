package com.shuibeizi.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 自定义值检查
 * @author: wanqing
 * @date: 2017/2/13 14:11
 */
public class MyStringUtils extends StringUtils {

    static final int[] intArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static boolean isNullOrZero(Integer value){
        if(null == value || value.intValue() == 0){
            return true;
        }
        return false;
    }

    public static boolean isNotNullAndZero(Integer value){
        return !MyStringUtils.isNotNullAndZero(value);
    }

    public static boolean isEmpty(Object value){
        if(null == value || (null != value && value.toString().equals(""))){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object value){
        return !MyStringUtils.isEmpty(value);
    }

    /**
     * null转空字符串
     * @param value
     * @return
     */
    public static String nullConvertEmpty(String value){
        if(null == value) return "";
        return value;
    }

    public static String quote(String arg){
        if(!StringUtils.isEmpty(arg)){
            return arg.replace("'","''");
        }
        return arg;
    }

    public static List<String> splitToList(String str, String separator){
        List<String> list = null;
        if (str == null) {
            return list;
        } else {
            int len = str.length();
            if (len == 0) {
                return list;
            } else {
                list = new ArrayList<String>();
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;

                while(true) {
                    while(i < len) {
                        if (separator.indexOf(str.charAt(i)) >= 0) {
                            if (match) {
                                list.add(str.substring(start, i));
                                match = false;
                                lastMatch = true;
                            }

                            ++i;
                            start = i;
                        } else {
                            lastMatch = false;
                            match = true;
                            ++i;
                        }
                    }

                    if (match && lastMatch) {
                        list.add(str.substring(start, i));
                    }
                    return list;
                }
            }
        }
    }
    /**
     * 获取随机数(时间戳+3位数)
     * @return
     */
    public static String getRandomStr(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.currentTimeMillis());
        Random random = new Random();
        int index = 0;
        for(int i = 0; i < 3; i++){
            index = random.nextInt(10);
            stringBuilder.append(intArray[index]);
        }
        return stringBuilder.toString();
    }

    /**
     * 组合成key-value形式
     * @param key
     * @param values
     * @return
     */
    public static String keyValueStr(String key, String... values){
        if(null == key || values.length <=0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        if(key.indexOf(";") > 0){
            String[] str = key.split(";");
            for(int i = 0; i < str.length; i++){
                if(StringUtils.isNotEmpty(str[i])){
                    stringBuilder.append(str[i] + ":" + values[i] + ";");
                }
            }
        }else {
            stringBuilder.append(key + ":" + values[0] + ";");
        }
        return stringBuilder.toString();
    }
}
