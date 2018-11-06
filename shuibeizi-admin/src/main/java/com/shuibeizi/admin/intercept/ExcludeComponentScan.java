package com.shuibeizi.admin.intercept;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/29 0029 16:32
 * @modified:
 */
@Component
@Aspect
public class ExcludeComponentScan {

    @Pointcut("execution(public * com.shuibeizi.*.api.*(..))")
    private void pointCut(){};
}
