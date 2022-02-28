package com.aorise.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author cat
 * @Description  解决跨域 options 请求不带session 出现302错误问题
 * @date  Created in 2018/9/26 15:37
 * @modified By:
 */
public class shiroOptionsFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
        if (!allowed) {
            // 判断请求是否是options请求
            String method = WebUtils.toHttp(request).getMethod();
            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
                return true;
            }
        }
        return allowed;
    }

}
