package com.trilemon.boss.infra.base.web.auth.shiro;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trilemon.boss.infra.base.BaseConstants;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.AppUser;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.boss.infra.base.model.dto.SignIn;
import com.trilemon.boss.infra.base.service.AppService;
import com.trilemon.boss.infra.base.service.TaobaoApiService;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss.infra.base.web.auth.TaobaoOauthException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class ShiroTaobaoAuthorizingRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(ShiroTaobaoAuthorizingRealm.class);
    @Autowired
    private AppService appService;
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private Environment env;

    /**
     * 获取淘宝 token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws
            AuthenticationException {
        checkNotNull("baseClient is null.", baseClient);

        ShiroTaobaoAuthenticationToken token = (ShiroTaobaoAuthenticationToken) authToken;

        AppUser appUser;
        if (Arrays.asList(env.getActiveProfiles()).contains("development")) {
            SignIn signIn = new SignIn();
            TaobaoSession taobaoSession = new TaobaoSession();
            taobaoSession.setTaobaoUserNick(token.getNick());
            taobaoSession.setTaobaoUserId(token.getUserId());
            taobaoSession.setAppKey(token.getAppKey());
            taobaoSession.setAccessToken(token.getAccessToken());
            taobaoSession.setRefreshToken(token.getRefreshToken());
            try {
                appUser = baseClient.signIn(taobaoSession, signIn);
            } catch (TaobaoSessionExpiredException e) {
                throw new AuthenticationException(e);
            } catch (TaobaoAccessControlException e) {
                throw new AuthenticationException(e);
            } catch (TaobaoEnhancedApiException e) {
                throw new AuthenticationException(e);
            }
        } else {
            try {
                appUser = baseClient.signIn(token);
            } catch (TaobaoOauthException e) {
                throw new AuthenticationException("can not get taobao token.", e);
            } catch (TaobaoSessionExpiredException e) {
                throw new AuthenticationException(e);
            } catch (TaobaoEnhancedApiException e) {
                throw new AuthenticationException(e);
            } catch (TaobaoAccessControlException e) {
                throw new AuthenticationException(e);
            }
        }
        PrincipalCollection principals;

        try {
            principals = createPrincipals(appUser);
        } catch (Exception e) {
            throw new AuthenticationException("Unable to obtain authenticated account properties.", e);
        }

        return new SimpleAuthenticationInfo(principals, "");
    }

    protected PrincipalCollection createPrincipals(AppUser appUser) {

        checkNotNull("appUser is null.", appUser);

        if (BaseConstants.APP_USER_STATUS_EXPIRED == appUser.getStatus()) {
            throw new AuthenticationException("appUser is expired, userId[" + appUser.getUserId() + "]");
        }
        Map<String, Object> props = Maps.newLinkedHashMap();

        props.put("appUser", appUser);

        Collection<Object> principals = Lists.newArrayListWithCapacity(1);
        principals.add(appUser.getUserId());
        principals.add(props);

        return new SimplePrincipalCollection(principals, getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        checkNotNull("baseClient is null.", baseClient);

        Collection c = principals.fromRealm(getName());

        AppUser appUser = (AppUser) c.iterator().next();

        if (null == appUser) {
            throw new AuthorizationException("appUser is null");
        }

        if (BaseConstants.APP_USER_STATUS_EXPIRED == appUser.getStatus()) {
            throw new AuthorizationException("appUser is expired, userId[" + appUser.getUserId() + "]");
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> accountRoles = resolveRoles(appUser);
        for (String roleName : accountRoles) {
            info.addRole(roleName);
        }

        Set<Permission> accountPermissions = resolvePermissions(appUser);
        for (Permission permission : accountPermissions) {
            info.addObjectPermission(permission);
        }

        if (CollectionUtils.isEmpty(info.getRoles()) &&
                CollectionUtils.isEmpty(info.getObjectPermissions()) &&
                CollectionUtils.isEmpty(info.getStringPermissions())) {
            //no authorization data associated with the Account
            //return null;
            return info;
        }

        return info;
    }

    private Set<Permission> resolvePermissions(AppUser appUser) {
        return Collections.emptySet();
    }

    //since 0.3
    private Set<String> resolveRoles(AppUser appUser) {
        return Collections.emptySet();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && ShiroTaobaoAuthenticationToken.class.isAssignableFrom(token.getClass());
    }

    public BaseClient getBaseClient() {
        return baseClient;
    }

    public void setBaseClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public TaobaoApiService getTaobaoApiService() {
        return taobaoApiService;
    }

    public void setTaobaoApiService(TaobaoApiService taobaoApiService) {
        this.taobaoApiService = taobaoApiService;
    }
}