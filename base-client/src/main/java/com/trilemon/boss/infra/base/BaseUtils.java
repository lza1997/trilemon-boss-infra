package com.trilemon.boss.infra.base;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.trilemon.boss.infra.base.model.dto.SignIn;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @author kevin
 */
public class BaseUtils {
    public static String buildHttpURI(String url, String path, List<NameValuePair> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http");
        uriBuilder.setHost(url);
        uriBuilder.setPath(path);
        for (NameValuePair nameValuePair : params) {
            uriBuilder.addParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static String buildHttpsURI(String url, String path, List<NameValuePair> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https");
        uriBuilder.setHost(url);
        uriBuilder.setPath(path);
        for (NameValuePair nameValuePair : params) {
            uriBuilder.addParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static String buildTaobaoOauthAuthorizeURI(String clientId, String responseType, String redirectUri,
                                                      String state, String view) {
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("response_type", responseType));
        params.add(new BasicNameValuePair("redirect_uri", redirectUri));
        params.add(new BasicNameValuePair("state", state));
        params.add(new BasicNameValuePair("view", view));
        return buildHttpsURI("oauth.taobao.com", "/authorize", params);
    }

    public static String buildDefaultTaobaoOauthAuthorizeURI(String clientId, String redirectUri, String state) {
        return buildTaobaoOauthAuthorizeURI(clientId, "code", redirectUri, state, "web");
    }

    public static SignIn state2SignIn(String state) {
        SignIn signIn = new SignIn();
        if (null == state) {
            return signIn;
        }
        List<String> segments = Splitter.on(";").trimResults().splitToList(state);
        for (String segment : segments) {
            String[] parameter = segment.split(":");
            if (parameter[0].equals("sign") && null != parameter[1]) {
                signIn.setSign(parameter[1]);
            }
            if (parameter[0].equals("versionNo") && null != parameter[1]) {
                signIn.setVersion(parameter[1]);
            }
            if (parameter[0].equals("itemCode") && null != parameter[1]) {
                signIn.setItemCode(parameter[1]);
            }
            if (parameter[0].equals("outer_trade_code") && null != parameter[1]) {
                signIn.setOuterTradeCode(parameter[1]);
            }
        }
        return signIn;
    }
}
