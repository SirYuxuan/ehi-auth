package com.yuxuan66.ehi.auth.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Sir丶雨轩
 * @date 2020/6/16
 */
public interface EhiAuth {


    Object getUser();

    List<String> getPermission();

    void noPermission(HttpServletRequest request, HttpServletResponse response,boolean isAjax,boolean noLogin);

}
