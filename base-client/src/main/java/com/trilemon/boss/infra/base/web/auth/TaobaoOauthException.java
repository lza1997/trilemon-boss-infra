package com.trilemon.boss.infra.base.web.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kevin
 */
public class TaobaoOauthException extends Exception {
    public TaobaoOauthException(Exception e) {
        super(e);
    }

    public TaobaoOauthException(String msg, Exception e) {
        super(msg, e);
    }

    public TaobaoOauthException(String msg) {
        super(msg);
    }

    public class ErrorMsg{
        @JsonProperty("error")
        private String error;
        @JsonProperty("error_description")
        private String errorDescription;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }
    }
}
