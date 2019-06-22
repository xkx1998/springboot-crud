package com.xkx.springboot.config;

import com.xkx.springboot.component.LoginHandlerInterceptor;
import com.xkx.springboot.component.MyLocaleResolver;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

//使用WebMvcConfigurationSupport可以扩展springMvc的功能

//springboot 2.0版本的WebMvcConfigurerAdapter过时了

//@EnableWebMvc   不要全面接管springmvc
//@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 修改Servlet容器的配置
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
            //定制嵌入式的Servlet容器无关的。
            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                factory.setPort(8083);
            }
        };
    }

    @Override

    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);

        //浏览器发送/xkx请求来到success
        registry.addViewController("/xkx").setViewName("success");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }


    //WebMvcConfigurationSupport
    @Bean //将组件注册在容器中
    public WebMvcConfigurerAdapter webMvcConfigurationSupport() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {


            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // super.addInterceptors(registry);
                //静态资源 *.css , *.js
                //SpringBoot已经做好静态资源的映射了
                /*registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/user/login","/webjars/**","/css/**","/js/**","/image/**");*/
            }
        };

        return adapter;
    }

    //把区域信息解析器添加到容器中
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
