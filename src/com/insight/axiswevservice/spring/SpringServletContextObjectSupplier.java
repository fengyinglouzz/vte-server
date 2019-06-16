package com.insight.axiswevservice.spring;
 
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletContext;
 import org.apache.axis2.AxisFault;
 import org.apache.axis2.ServiceObjectSupplier;
 import org.apache.axis2.description.AxisService;
 import org.apache.axis2.description.Parameter;
 import org.apache.axis2.engine.AxisConfiguration;
 import org.apache.axis2.i18n.Messages;
 import org.apache.axis2.transport.http.HTTPConstants;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.context.ApplicationContext;
 import org.springframework.web.context.support.WebApplicationContextUtils;
 
 public class SpringServletContextObjectSupplier
   implements ServiceObjectSupplier
 {
   private static Log log = LogFactory.getLog(SpringServletContextObjectSupplier.class);
   public static final String SERVICE_SPRING_BEANNAME = "SpringBeanName";
   public Object getServiceObject(AxisService axisService)
     throws AxisFault
   {
     try
     {
       Parameter implBeanParam = axisService.getParameter("SpringBeanName");
       String beanName = ((String)implBeanParam.getValue()).trim();
       if (beanName != null)
       {
         Parameter servletConfigParam = axisService.getAxisConfiguration().getParameter(HTTPConstants.HTTP_SERVLETCONFIG);
         
         if (servletConfigParam == null) {
           throw new Exception("Axis2 Can't find ServletConfigParameter");
         }
         Object obj = servletConfigParam.getValue();
         ServletContext servletContext;
         if ((obj instanceof ServletConfig)) {
           ServletConfig servletConfig = (ServletConfig)obj;
           servletContext = servletConfig.getServletContext();
         } else {
           throw new Exception("Axis2 Can't find ServletConfig");
         }
         ApplicationContext aCtx =  WebApplicationContextUtils.findWebApplicationContext(servletContext);
         if (aCtx == null) {
           log.warn("Axis2 Can't find Spring's ApplicationContext");
           return null; }
         if (aCtx.getBean(beanName) == null) {
           throw new Exception("Axis2 Can't find Spring Bean: " + beanName);
         }
         return aCtx.getBean(beanName);
       }
       
       throw new AxisFault(Messages.getMessage("paramIsNotSpecified", "SERVICE_SPRING_BEANNAME"));
     }
     catch (Exception e) {
       throw AxisFault.makeFault(e);
     }
   }
 }





