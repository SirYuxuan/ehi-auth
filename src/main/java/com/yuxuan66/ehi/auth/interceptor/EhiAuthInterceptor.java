package com.yuxuan66.ehi.auth.interceptor;

import com.yuxuan66.ehi.auth.annotation.Auth;
import com.yuxuan66.ehi.auth.common.EhiAuth;
import com.yuxuan66.ehi.auth.utils.StrUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Sir丶雨轩
 * @date 2020/6/16
 */
public class EhiAuthInterceptor implements HandlerInterceptor {


    private EhiAuth ehiAuth;

    public EhiAuthInterceptor(EhiAuth ehiAuth) {
        this.ehiAuth = ehiAuth;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod handlerMethod = null;

        // 如果请求的不是方法 则直接跳过当前拦截器
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        handlerMethod = (HandlerMethod) handler;

        // 获取当前请求的方法
        Method method = handlerMethod.getMethod();

        // 获取方法上注解
        Auth auth = method.getAnnotation(Auth.class);

        // 如果方法没有注解,则去类上去获取 如果方法有则使用方法的注解,方法的注解优先级高于类的注解
        if (auth == null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }

        // 如果没有Auth注解或手动声明跳过 则跳过鉴权
        if (auth == null || auth.skip()) {
            return true;
        }

        String authorityCode = auth.value();

        // 如果不包含权限编码,则表示这个接口登录就可以访问
        if (StrUtil.isBlank(authorityCode)) {

            Object user = ehiAuth.getUser();

            // 用户还没有登录
            if (user == null) {
                ehiAuth.noPermission(request, response, isAjax(request),true);
                return false;
            }

            // 当前用户已经登录 完成鉴权流程
            return true;
        }

        List<String> permissionList = ehiAuth.getPermission();


        // 当前用户没有这个地址的访问权限
        if (!permissionList.contains(authorityCode)) {
            ehiAuth.noPermission(request, response, isAjax(request),false);
            return false;
        }

        return true;

    }


    /**
     * 判断请求是否为ajax请求
     *
     * @param request HttpServletRequest
     * @return Boolean
     */
    public boolean isAjax(HttpServletRequest request) {
        return request.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }

}
