package com.trilemon.boss360.infrastructure.base.service.api.exception;

/**
 * @author kevin
 */
public class DefaultTaobaoApiExceptionHandler extends TaobaoApiExceptionHandler {
    @Override
    public void handlerApiException(TaobaoEnhancedApiException e) {
    }

    @Override
    public void handlerSessionExpired(TaobaoSessionExpiredException e) {
    }

    @Override
    public void handlerAccessControl(TaobaoAccessControlException e) {
    }
}
