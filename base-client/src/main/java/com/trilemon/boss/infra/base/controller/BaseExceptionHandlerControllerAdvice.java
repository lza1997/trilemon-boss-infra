package com.trilemon.boss.infra.base.controller;

import com.google.common.base.Throwables;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author kevin
 */
@ControllerAdvice
public class BaseExceptionHandlerControllerAdvice {
    @ExceptionHandler
    public
    @ResponseBody
    String handleException(Exception ex, WebRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "Unknown error occurred: " + Throwables.getStackTraceAsString(ex);
    }

    /**
     * 处理淘宝的 session 过期情况（重新登录）
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(TaobaoSessionExpiredException.class)
    public
    @ResponseBody
    String handleSessionExpiredException(TaobaoSessionExpiredException ex, WebRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        //跳转到登录页面
        return "Unknown error occurred: " + Throwables.getStackTraceAsString(ex);
    }

//    /**
//     * 处理淘宝流控
//     * @param ex
//     * @param request
//     * @param response
//     * @return
//     */
//    @ExceptionHandler(TaobaoAccessControlException.class)
//    public
//    @ResponseBody
//    String handleSessionExpiredException(TaobaoAccessControlException ex, WebRequest request, HttpServletResponse response) {
//        response.setHeader("Content-Type", "application/json");
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        //跳转到登录页面
//        return "Unknown error occurred: " + Throwables.getStackTraceAsString(ex);
//    }
}
