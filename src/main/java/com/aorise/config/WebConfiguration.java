package com.aorise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author dell
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

//
//    @Value("${file.staticAccessPath}")
//    private String staticAccessPath;
//
//    @Value("${file.uploadFolder}")
//    private String uploadFolder;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer()
//    {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").
//                        allowedOrigins("*"). //允许跨域的域名，可以用*表示允许任何域名使用
//                        allowedMethods("*"). //允许任何方法（post、get等）
//                        allowedHeaders("*"). //允许任何请求头
//                        allowCredentials(true). //带上cookie信息
//                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
//            }
//        };
//    }


    /**
     * 解决跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
//                .allowedOrigins("*")
                //               .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    /**
     * 异步请求配置
     *
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }

    /**
     * 配置拦截器、拦截路径
     * 每次请求到拦截的路径，就会去执行拦截器中的方法
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
//        excludePath.add("/**");     //全部

        //接口
        excludePath.add("/api/login");
        excludePath.add("/api/logout");
        excludePath.add("/api/403");
        excludePath.add("/api/fileUpload/**");
        excludePath.add("/api/wechatUser/**");
        excludePath.add("/api/scenic/**");
        excludePath.add("/api/activity/**");
        excludePath.add("/api/news/**");
        excludePath.add("/api/question");
        excludePath.add("/api/checkPoint/**");
        excludePath.add("/api/checkPointRecord/pageIndex/**");
        excludePath.add("/api/member/**");
        excludePath.add("/api/route");
        excludePath.add("/api/message/pageIndex/**");
        excludePath.add("/api/banner/**");
        excludePath.add("/api/config");


        //swagger
        excludePath.add("/swagger-ui.html");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/v2/api-docs");

        excludePath.add("/webjars/**");
        excludePath.add("/static/**");
        excludePath.add("/ue/upload");
        excludePath.add("/uploadFile/**");
        excludePath.add("/files/**");
        excludePath.add("/pic/**");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
