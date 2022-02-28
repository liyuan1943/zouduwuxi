package com.aorise.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author cat
 * @Description  继承会话管理  解决跨平台  session会话管理问题
 * @date  Created on 2018/7/9.
 * @modified By:
 */
public class myShiroSessionManager  extends DefaultWebSessionManager {
    private static final String TOKEN = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public myShiroSessionManager() {
        super();
    }
    //重写 getSessionId()
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = /*WebUtils.toHttp(request).getHeader(TOKEN)*/request.getParameter(TOKEN);
        //如果请求头中有 TOKEN 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
