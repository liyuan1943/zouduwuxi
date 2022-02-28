package com.aorise.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author cat
 * @Description   shiro 配置
 * @date  Created on 2018/7/9.
 * @modified By:
 */
@Configuration
public class ShiroConfiguration {
    //将自己的验证方式加入容器
    @Bean
    public ShiroRealm myShiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
//        DefaultSecurityManager se环境  默认使用DefaultSessionManager
//        ServletContainerSessionManager web环境 默认ServletContainerSessionManager 直接使用servlet容器的session
//        DefaultWebSecurityManager web环境 默认DefaultWebSessionManager  shiro自己维护会话，与servlet容器无关
        DefaultWebSecurityManager securitydManager = new DefaultWebSecurityManager();
        securitydManager.setRealm(myShiroRealm());
        securitydManager.setSessionManager(sessionManager());//启用session  管理 DefaultWebSessionManager
//        securitydManager.setCacheManager(cacheManager());//使用shiro自带的缓存，当然也可以使用redis等缓存数据库
        return securitydManager;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        myShiroSessionManager mySessionManager = new myShiroSessionManager();
        //加入插件自定义的sessionDAO
//        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securitydManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securitydManager);
        Map<String, String> map = new LinkedHashMap<String, String>();//一定要使用LinkedHashMap，有序的
//        三种方式实现定义权限路径
//        第一种:使用角色名定义
//        map.put("/**/**", "authc, roles[admin]");

//        第二种:使用权限code定义
//        map.put("/api/update", "authc, perms[UPDATE]");//对于restful接口 最好不要在这里定义，因为一个链接可以有不同的请求方式
//        map.put("/api/delete", "authc, perms[DELETE]");
//        map.put("/api/add", "authc, perms[ADD]");
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new shiroOptionsFilter());//将自定义 的FormAuthenticationFilter注入shiroFilter中

        // 配置不需要登录认证的链接 顺序判断
        map.put("/static/**", "anon");
        map.put("/ue/upload", "anon");
        map.put("/uploadFile/**", "anon");
        map.put("/files/**", "anon");
        map.put("/pic/**", "anon");
        map.put("/swagger-ui.html/**", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/api/403", "anon");
        map.put("/api/fileUpload/**", "anon");

        map.put("/api/AreaInfo/**", "anon");
        map.put("/api/member/**", "anon");
        map.put("/api/goods/**", "anon");
        map.put("/api/category/**", "anon");
        map.put("/api/property/**", "anon");
        map.put("/api/categoryProperty/**", "anon");
//        map.put("/api/travel/**", "anon");
        map.put("/api/travelOrder/**", "anon");
        map.put("/api/travelType/**", "anon");
        map.put("/api/travelThem/**", "anon");
        map.put("/api/wechatUser/**", "anon");
        map.put("/api/upload/**", "anon");
        map.put("/core/**", "anon");

        map.put("/api/login", "anon");
        //登出,shiro已经实现
        //成功后重定向到主页  跨域不可用
        map.put("/logout", "logout");

//      <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->;
//      <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问 使用@RequiresPermissions("")配置接口权限-->
//        map.put("/**", "authc");
        map.put("/**", "anon");

//        设置登录url，，未登录时请求的
        shiroFilterFactoryBean.setLoginUrl("/api/unauth");
//        登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权跳转页面，但是用注解RequiresPermissions配置的权限不通过会抛出AuthorizationException异常，并不会跳转，需要做相应的异常处理
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入shiro注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        AuthorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return AuthorizationAttributeSourceAdvisor;
    }

    //DefaultAdvisorAutoProxyCreator是Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {

        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
   /* public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }*/

    /**
     * 配置shiro redisManager
     * 使用shiro-redis开源插件
     *
     * @return
     */
   /* public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.1.41");
        redisManager.setPort(6379);
        redisManager.setPassword("aoriseredis123456");
        redisManager.setExpire(1800);// 配置缓存过期时间 单位s
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }*/

    //使用redis-shiro开源插件  重写的sessionDAO，  用redis数据库管理session的持久化,分布式系统中可以共享session
  /*  @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }*/
}
