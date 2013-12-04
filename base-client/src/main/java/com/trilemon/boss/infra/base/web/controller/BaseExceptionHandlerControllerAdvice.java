package com.trilemon.boss.infra.base.web.controller;

import com.google.common.base.Throwables;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author kevin
 */
@ControllerAdvice
public class BaseExceptionHandlerControllerAdvice {
    private final static Logger logger = LoggerFactory.getLogger(BaseExceptionHandlerControllerAdvice.class);

    @ExceptionHandler
    public String handleException(Exception ex, WebRequest request, HttpServletResponse response) {
        logger.error("handleException: ", ex);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "Unknown error occurred: " + Throwables.getStackTraceAsString(ex);
    }

    @ExceptionHandler(TaobaoSessionExpiredException.class)
    public String handleTaobaoSessionExpiredException(TaobaoSessionExpiredException ex,
                                                      WebRequest request,
                                                 HttpServletResponse response) {
        logger.error("handleException: ", ex);
        //检查是否是服务到期了还是 session 到期了
        return "forward:/session";
    }

    @ExceptionHandler(TaobaoAccessControlException.class)
    public String handleTaobaoAccessControlException(TaobaoAccessControlException ex, WebRequest request, HttpServletResponse response) {
        logger.error("handleException: ", ex);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "Unknown error occurred: " + Throwables.getStackTraceAsString(ex);
    }
}
