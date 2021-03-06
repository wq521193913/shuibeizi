package com.shuibeizi.sys.read.intercept;

import com.shuibeizi.common.exception.CustomException;
import com.shuibeizi.common.util.ApiResult;
import net.sf.json.JSONArray;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/25 0025 14:34
 * @modified:
 */
@Component
@Aspect
public class ApiAspectj {

    private static final Logger logger = LoggerFactory.getLogger(ApiAspectj.class);

    @Pointcut("execution(public * com.shuibeizi.sys.api.*.*(..))")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        logger.info("=== DateTime: {} ===", DateFormatUtils.format(System.currentTimeMillis(),DateFormatUtils.ISO_DATETIME_FORMAT.getPattern()));
        logger.info("=== Method: {} ===",joinPoint.getSignature().getName());
        logger.info("=== Controller: {} ===", joinPoint.getSignature().getDeclaringTypeName());
        logger.info("=== Parameters: {} ===", JSONArray.fromObject(joinPoint.getArgs()));
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ApiResult result = new ApiResult();
        try {
           return proceedingJoinPoint.proceed();
        }catch (CustomException ce){
            logger.error("{} error:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(),ExceptionUtils.getStackTrace(ce));
            result = ApiResult.getSystemErrorMsg(ce);
        }catch (Exception e){
            logger.error("{} error:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(),ExceptionUtils.getStackTrace(e));
            result = ApiResult.getSystemErrorMsg();
        }
        return result;
    }

}
