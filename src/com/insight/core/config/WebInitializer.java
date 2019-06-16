package com.insight.core.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
import org.apache.axis2.transport.http.AxisServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 
 */
public class WebInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8",true));
        FilterRegistration.Dynamic dynamicFilter =  servletContext.addFilter("vteFilter", new VteRequestFileter("UTF-8",true));
        dynamicFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "*");
      
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        
        ServletRegistration.Dynamic axisServlet = servletContext.addServlet("axisServlet", new AxisServlet());
        axisServlet.addMapping("/services/*");
        axisServlet.setLoadOnStartup(1);
        
        ctx.setServletContext(servletContext);
        ctx.register(MyMVCConfig.class);
        
    }

}
