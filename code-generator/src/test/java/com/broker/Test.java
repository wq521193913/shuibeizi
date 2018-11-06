package com.broker;

import java.util.Date;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/3/23 0023 13:50
 * @modified:
 */
public class Test {

    public static void main(String[] args){
        int index =0;
        while (true){
            index++;
            String str = String.valueOf(new Date().getTime());
            String[] strArray = str.split("");
            int l = 0;
            for(String s : strArray){
//            System.out.println(s);
                l+=Integer.valueOf(s);
            }
            System.out.println("index:"+index+";l:"+l);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
