package com.trilemon.boss360.infrastructure.base.service.api.exception;

/**
 * @author kevin
 */
public abstract class TaobaoApiExceptionHandler {

    public void handler(Long userId, BaseTaobaoApiException e) {
        if (e instanceof TaobaoSessionExpiredException) {
            TaobaoSessionExpiredException taobaoSessionExpiredException = (TaobaoSessionExpiredException) e;
            taobaoSessionExpiredException.setUserId(userId);
            handlerSessionExpired(taobaoSessionExpiredException);
        } else if (e instanceof TaobaoAccessControlException) {
            handlerAccessControl((TaobaoAccessControlException) e);
        } else if (e instanceof TaobaoEnhancedApiException) {

        }
    }

    public void handler(BaseTaobaoApiException e) {
        handler(null, e);
    }

    abstract void handlerApiException(TaobaoEnhancedApiException e);

    abstract void handlerSessionExpired(TaobaoSessionExpiredException e);

    abstract void handlerAccessControl(TaobaoAccessControlException e);
}
