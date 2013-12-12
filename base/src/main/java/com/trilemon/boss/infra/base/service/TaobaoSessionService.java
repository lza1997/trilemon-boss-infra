package com.trilemon.boss.infra.base.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.trilemon.boss.infra.base.BaseUtils;
import com.trilemon.boss.infra.base.dao.AppUserDAO;
import com.trilemon.boss.infra.base.dao.AppUserSignInLogDAO;
import com.trilemon.boss.infra.base.dao.TaobaoSessionDAO;
import com.trilemon.boss.infra.base.model.AppUser;
import com.trilemon.boss.infra.base.model.AppUserSignInLog;
import com.trilemon.boss.infra.base.model.TaobaoApp;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.boss.infra.base.model.dto.SignIn;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss.infra.base.util.TopApiUtils;
import com.trilemon.boss.infra.base.web.auth.TaobaoOauthException;
import com.trilemon.boss.infra.base.web.auth.shiro.ShiroTaobaoAuthenticationToken;
import com.trilemon.commons.DateUtils;
import com.trilemon.commons.Encodes;
import com.trilemon.commons.JsonMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

import static com.trilemon.boss.infra.base.BaseConstants.APP_USER_STATUS_EXPIRED;
import static com.trilemon.boss.infra.base.BaseConstants.APP_USER_STATUS_NORMAL;

/**
 * @author kevin
 */
@Service
public class TaobaoSessionService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoSessionService.class);
    @Autowired
    private TaobaoSessionDAO taobaoSessionDAO;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private AppService appService;
    @Autowired
    private TaobaoAppService taobaoAppService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserSignInLogDAO appUserSignInLogDAO;
    @Autowired
    private TaobaoShopService taobaoShopService;
    @Autowired
    private Environment environment;
    @Autowired
    private TaobaoApiService taobaoApiService;

    public TaobaoSession getTaobaoSession(Long userId, Long subUserId, String appKey) {
        return taobaoSessionDAO.selectByTaobaoUserIdAndAppKey(userId, subUserId, appKey);
    }

    /**
     * 刷新 session
     *
     * @param userId
     * @param appKey
     * @throws Exception
     */
    public void refreshSession(Long userId, String appKey) throws Exception {
        AppUser appUser = appUserDAO.selectByUserIdAndAppKey(userId, appKey);
        if (appUser.getStatus() == APP_USER_STATUS_EXPIRED) {
            return;
        }
        if (appUser.getDeadline().getTime() < appService.getLocalSystemTime().toDate().getTime()) {
            appUserService.expire(userId, appKey);
            return;
        }
        TaobaoSession taobaoSession;
        if (appUser.getSubAccount()) {
            taobaoSession = taobaoSessionDAO.selectByTaobaoUserIdAndAppKey(appUser.getParentUserId(), appUser.getUserId(),
                    appUser.getAppKey());
        } else {
            taobaoSession = taobaoSessionDAO.selectByTaobaoUserIdAndAppKey(appUser.getUserId(), null, appUser.getAppKey());
        }
        if (null == taobaoSession) {
            return;
        }
        TaobaoApp taobaoApp = taobaoAppService.getTaobaoApp(taobaoSession.getAppKey());
        TaobaoSession newTaobaoSession = TopApiUtils.refreshSessionKey(taobaoApp.getAppKey(), taobaoApp.getAppSecret(),
                taobaoSession.getAccessToken(),
                taobaoSession.getRefreshToken());
        newTaobaoSession.setAppKey(appKey);
        if (appUser.getSubAccount()) {
            newTaobaoSession.setTaobaoUserId(appUser.getParentUserId());
            newTaobaoSession.setSubTaobaoUserId(appUser.getUserId());
        } else {
            newTaobaoSession.setTaobaoUserId(appUser.getUserId());
        }
        taobaoSessionDAO.updateByTaobaoUserIdAndAppKey(newTaobaoSession);
        logger.info("refresh session, taobaoSession[{}]", ToStringBuilder.reflectionToString(newTaobaoSession));
    }

    public void insertOrUpdateTaobaoSession(TaobaoSession taobaoSession) {
        Preconditions.checkNotNull(taobaoSession.getTaobaoUserId(), "taobao user id is null.");
        Preconditions.checkNotNull(taobaoSession.getAppKey(), "taobao app key is null.");
        TaobaoSession existTaobaoSession = taobaoSessionDAO.selectByTaobaoUserIdAndAppKey(taobaoSession.getTaobaoUserId(),
                taobaoSession.getSubTaobaoUserId(),
                taobaoSession.getAppKey());

        if (null == existTaobaoSession) {
            taobaoSessionDAO.insertSelective(taobaoSession);
        } else {
            taobaoSessionDAO.updateByTaobaoUserIdAndAppKey(taobaoSession);
        }
    }

    public TaobaoSession getTaobaoSessionBySubTaobaoUserId(Long subTaobaoUserId, String appKey) {
        return taobaoSessionDAO.selectBySubTaobaoUserId(subTaobaoUserId, appKey);
    }

    /**
     * 登录
     *
     * @param taobaoSession
     * @param signIn
     * @return
     */
    //TODO 主帐号进来的时候就已经对子帐号授权了
    @Transactional
    public AppUser signIn(TaobaoSession taobaoSession, SignIn signIn) throws TaobaoAccessControlException, TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        Long userId = taobaoSession.getSubTaobaoUserId();
        String nick = taobaoSession.getSubTaobaoUserNick();
        if (null == userId || 0 == userId) {
            userId = taobaoSession.getTaobaoUserId();
            nick = taobaoSession.getTaobaoUserNick();
        }

        TaobaoApp taobaoApp = taobaoAppService.getTaobaoApp(taobaoSession.getAppKey());
        //更新用户记录
        AppUser appUser = appUserDAO.selectByUserIdAndAppKey(userId, taobaoSession.getAppKey());

        if (null == appUser) {
            appUser = new AppUser();
            appUser.setUserId(userId);
            appUser.setAddTime(appService.getLocalSystemTime().toDate());
            appUser.setAppKey(taobaoSession.getAppKey());
            appUser.setArticleCode(taobaoApp.getArticleCode());

            if (Lists.newArrayList(environment.getActiveProfiles()).contains("development")) {
                appUser.setDeadline(DateUtils.parse("2029-01-01", DateUtils.yyyyMMdd2).toDate());
            } else {
                //TODO 调用服务api
            }
            appUser.setVersion(signIn.getVersion());
            appUser.setItemCode(signIn.getItemCode());
            appUser.setLastSignInIp(signIn.getSignInIp());
            appUser.setLastSignInTime(appService.getLocalSystemTime().toDate());
            appUser.setNick(nick);
            appUser.setStatus(APP_USER_STATUS_NORMAL);
            if (null != taobaoSession.getSubTaobaoUserId()) {
                appUser.setSubAccount(true);
                appUser.setParentUserId(taobaoSession.getTaobaoUserId());
            }
            appUserDAO.insertSelective(appUser);

        }
        insertOrUpdateTaobaoSession(taobaoSession);
        taobaoShopService.insertOrUpdateSeller(userId);
        taobaoShopService.insertOrUpdateShop(userId, nick);

        //插入登录流水
        AppUserSignInLog appUserSignInLog = new AppUserSignInLog();
        appUserSignInLog.setUserId(userId);
        appUserSignInLog.setAddTime(appService.getLocalSystemTime().toDate());
        appUserSignInLog.setArticleCode(taobaoApp.getArticleCode());
        appUserSignInLog.setItemCode(signIn.getItemCode());
        appUserSignInLog.setOuterTradeCode(signIn.getOuterTradeCode());
        appUserSignInLog.setScope(signIn.getScope());
        appUserSignInLog.setSign(signIn.getSign());
        appUserSignInLog.setSignInIp(signIn.getSignInIp());
        appUserSignInLog.setSignInTime(appService.getLocalSystemTime().toDate());
        appUserSignInLogDAO.insertSelective(appUserSignInLog);

        return appUser;
    }

    //TODO test
    public AppUser singIn(ShiroTaobaoAuthenticationToken token) throws TaobaoOauthException, TaobaoSessionExpiredException, TaobaoAccessControlException, TaobaoEnhancedApiException {
        SignIn signIn = BaseUtils.state2SignIn(token.getState());
        signIn.setSignInIp(token.getHost());
        TaobaoApp taobaoApp = taobaoAppService.getTaobaoApp(token.getAppKey());
        TaobaoSession taobaoSession = authToken(token, taobaoApp);
        return signIn(taobaoSession, signIn);

    }

    /**
     * 获取 token
     *
     * @param token
     * @param taobaoApp
     * @return
     * @throws com.trilemon.boss.infra.base.web.auth.TaobaoOauthException
     *
     */
    public TaobaoSession authToken(ShiroTaobaoAuthenticationToken token, TaobaoApp taobaoApp) throws TaobaoOauthException {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }

                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            boolean error;

            URL url = new URL("https://oauth.taobao.com/token?grant_type=authorization_code&client_id=" +
                    taobaoApiService.getAppKey() + "&client_secret=" + taobaoApp.getAppSecret() +
                    "&code=" + token.getCode() + "&redirect_uri=" + token.getRedirectUri());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setConnectTimeout(30000);
            httpsURLConnection.setReadTimeout(30000);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setRequestMethod("POST");

            httpsURLConnection.connect();

            int responseCode = httpsURLConnection.getResponseCode();
            InputStream input;
            if (responseCode == 200) {
                error = false;
                input = httpsURLConnection.getInputStream();
            } else {
                error = true;
                input = httpsURLConnection.getErrorStream();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            if (error) {
                throw new TaobaoOauthException(result.toString());
            }
            String json = result.toString();
            TaobaoSession taobaoSession = JsonMapper.nonEmptyMapper().fromJson(json, TaobaoSession.class);
            taobaoSession.setTaobaoUserNick(new String(Encodes.decodeBase64(taobaoSession.getTaobaoUserNick())));
            taobaoSession.setSubTaobaoUserNick(new String(Encodes.decodeBase64(taobaoSession.getSubTaobaoUserNick())));
            taobaoSession.setAppKey(taobaoApp.getAppKey());
            return taobaoSession;
        } catch (Exception e) {
            //TODO error format
            //"error": "invalid_client",  "error_description": "authorize code ShK5rw2hrJDUH2VS4ce11oWx407464 invalidate,please authorize again."}
            throw new TaobaoOauthException(e);
        }
    }
}
