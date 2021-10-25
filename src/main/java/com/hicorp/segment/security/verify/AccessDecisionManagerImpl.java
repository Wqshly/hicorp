package com.hicorp.segment.security.verify;

import com.hicorp.segment.security.services.GrantedAuthorityImpl;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @Author: wqs
 * @Date: Created in 15:53 2021/5/5
 * @Description: AccessDecisionManager: Makes a final access control (authorization) decision.
 * @ChineseDescription: 通过实现该接口，做出最终的访问控制（授权）决策。
 * @Modified By: wqs, 20:13 2021/05/06.
 */
@Service
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    /**
     * @Author: 91074
     * @Date: Created in 19:34 2021/5/9
     * @Params: [authentication, o, collection] [org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection<org.springframework.security.access.ConfigAttribute>]
     * @Return: void
     * @Description: Resolves an access control decision for the passed parameters.
     * @ChineseDescription: 为传递的参数解析访问控制决策。
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();

        String url, method;
        AntPathRequestMatcher matcher;

//        if (null == collection || collection.size() <= 0) {
//            return;
//        }

//        ConfigAttribute c;
//        String needRole;

//        for (ConfigAttribute configAttribute : collection) {
//            c = configAttribute;
//            needRole = c.getAttribute();

        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga instanceof GrantedAuthorityImpl gai) {

                url = gai.getUrl();
                method = gai.getMethod();

                matcher = new AntPathRequestMatcher(url);

                if (matcher.matches(request)) {
                    if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                        return;
                    }
                }

            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {

                //未登录允许访问 login 接口
                matcher = new AntPathRequestMatcher("/login");
                if (matcher.matches(request)) {
                    return;
                }
                //未登录允许访问 修改密码 接口
                matcher = new AntPathRequestMatcher("/user/setPassword/list");
                if (matcher.matches(request)) {
                    return;
                }

            }

//                if (needRole.trim().equals(ga.getAuthority())) {
//                    return;
//                }

//            }
        }

        throw new AccessDeniedException("no right");

    }

    /**
     * @Author: 91074
     * @Date: Created in 19:34 2021/5/9
     * @Params: [configAttribute] [org.springframework.security.access.ConfigAttribute]
     * @Return: boolean
     * @Description: Indicates whether this AccessDecisionManager is able to process authorization requests presented with the passed ConfigAttribute.
     * @ChineseDescription: 指示此 AccessDecisionManager 实现，是否能够处理随传递的ConfigAttribute呈现的授权请求。
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }


    /**
     * @Author: 91074
     * @Date: Created in 19:35 2021/5/9
     * @Params: [aClass] [java.lang.Class<?>]
     * @Return: boolean
     * @Description: Indicates whether the AccessDecisionManager implementation is able to provide access control decisions for the indicated secured object type.
     * @ChineseDescription: 指示 AccessDecisionManager 接口的实现，是否能够为指定的安全对象类型提供访问控制决策。
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
