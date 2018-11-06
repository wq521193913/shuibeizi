package com.shuibeizi.admin.intercept;

import com.shuibeizi.common.enumerate.ResultCode;
import com.shuibeizi.common.util.RedisUtils;
import com.shuibeizi.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Administrator
 * @description:
 * @date: Create in 2018/10/28 0028 上午 10:06
 * @modified:
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(request.getRequestURI().indexOf("/static") > 0){
//            //不拦截的方法可以用/static前缀
//            return super.preHandle(request, response, handler);
//        }
//        String token = request.getHeader("token");
//        if(null != token && redisUtils.exists(token)){
//            return super.preHandle(request, response, handler);
//        }
//        Result result = new Result(ResultCode.LOGIN_EXPIRE);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=UTF-8");
//        response.getWriter().print(result.toString());
//        return false;

        return super.preHandle(request, response, handler);
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
