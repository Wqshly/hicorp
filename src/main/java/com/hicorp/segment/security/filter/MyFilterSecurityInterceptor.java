package com.hicorp.segment.security.filter;

import com.hicorp.segment.security.verify.AccessDecisionManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: wqs
 * @Date: Created in 14:47 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    private FilterInvocationSecurityMetadataSource metadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.invoke(new FilterInvocation(request, response, chain));
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
//        if (this.isApplied(filterInvocation) && this.observeOncePerRequest) {
//            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
//        } else {
            if (filterInvocation.getRequest() != null) {
                filterInvocation.getRequest().setAttribute("__spring_security_filterSecurityInterceptor_filterApplied", Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(filterInvocation);

            try {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
//        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.metadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.metadataSource;
    }

    //设置自定义的 FilterInvocationSecurityMetadataSource
    @Autowired
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        this.metadataSource = newSource;
    }

    //设置自定义的AccessDecisionManager
    @Autowired
    public void setAccessDecisionManagerImpl(AccessDecisionManagerImpl accessDecisionManagerImpl) {
        super.setAccessDecisionManager(accessDecisionManagerImpl);
    }
}
