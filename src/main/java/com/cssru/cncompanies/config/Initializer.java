package com.cssru.cncompanies.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Initializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "mainservlet";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // регистрация конфигураций в Spring контексте
        ctx.register(WebAppConfig.class);
        ctx.register(SecurityConfig.class);
        ContextLoaderListener listener = new ContextLoaderListener(ctx);
        servletContext.addListener(listener);
        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
        servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
        ctx.setServletContext(servletContext);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME, dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}