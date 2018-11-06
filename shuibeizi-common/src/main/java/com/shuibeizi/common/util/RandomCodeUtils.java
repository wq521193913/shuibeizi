package com.shuibeizi.common.util;

import java.util.UUID;

/**
 * @author: Administrator
 * @description: 获取随机码
 * @date: Create in 2018/3/24 0024 上午 10:42
 * @modified:
 */
public class RandomCodeUtils {

    private static Integer lock = 0;

    private static RandomCodeUtils randomCodeUtils = null;

    private String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    private RandomCodeUtils(){}

    private static class InstanceObj{
        private static final RandomCodeUtils instance = new RandomCodeUtils();
    }

    public static RandomCodeUtils getInstance(){
        return InstanceObj.instance;
    }

    public String getRandomCode(){
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static void main(String[] args){
        System.out.println(UUID.randomUUID());
        System.out.println(RandomCodeUtils.getInstance().getRandomCode());
    }


}
