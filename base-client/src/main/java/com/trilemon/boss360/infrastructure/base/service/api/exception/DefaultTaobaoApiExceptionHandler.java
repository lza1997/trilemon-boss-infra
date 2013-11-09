package com.trilemon.boss360.infrastructure.base.service.api.exception;

/**
 * @author kevin
 */
public class DefaultTaobaoApiExceptionHandler extends TaobaoApiExceptionHandler {
    @Override
    void handlerApiException(TaobaoEnhancedApiException e) {
    }

    @Override
    void handlerSessionExpired(TaobaoSessionExpiredException e) {
    }

    @Override
    void handlerAccessControl(TaobaoAccessControlException e) {
    }
}
