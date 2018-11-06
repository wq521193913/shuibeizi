package com.shuibeizi.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机生成Token值
 */
public class TokenGenerate {
    private final String randomContent = "1234567890abcdefghijkmopqrstuvwxyzABCDEFGHIJKMOPGRSTUVWXYZ@";
    private final int len = 32;
    private TokenGenerate(){}

    private static class InstanceObj{
        private static final TokenGenerate instance = new TokenGenerate();
    }

    public static TokenGenerate getInstance(){
        return InstanceObj.instance;
    }

    public String generate() {
        return this.generate(len, randomContent);
    }

    public String generate(int len) {
        return this.generate(len, randomContent);
    }

    public String generate(int len, String randomContent) {
        return RandomStringUtils.random(len, randomContent);
    }
}
