package com.insight.core.config;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.insight.core.common.Constants;
import com.insight.wisehealth.vte.common.SessionUser;
import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;

/**
 * @author wangzhuzhu
 * 
 */
@Aspect
@Service
@EnableAspectJAutoProxy
public class DaoAspectAop {

    /**
     * 申明切点，同时配置将要被aop过滤的业务类
     */
    @Pointcut("execution (* com.insight.wisehealth.vte.dao.*.insert*(..) )")
    public void pointcut() {
    	
    }	
    /**
     * 申明切点，同时配置将要被aop过滤的业务类
     */
    @Pointcut("execution (* com.insight.wisehealth.vte.dao.*.save*(..) )")
    public void pointcutsave() {
    	
    }
    @Before("pointcut()")
    public void doBefore(JoinPoint point) {
    	//获取参数值
    	Object[] object = point.getArgs();
    	String methodName = point.getSignature().getName();      
    	LoginUserPojo loginUserPojo = SessionUser.getCurrentUser();
    	if(object!=null&&object.length>0){
    		if(methodName.startsWith("insert")){
    			if(object[0].getClass().getName().startsWith("com.insight.wisehealth")){
    				Class<?> classType = object[0].getClass();
					try {//设置创建时间
						Method setMethod = classType.getMethod("setCreateDt", new Class[] { java.util.Date.class });
        				setMethod.invoke(object[0], new Object[] { new Date() });
					} catch (Exception e) {
						//e.printStackTrace();
					}
					if(loginUserPojo!=null){
						try {//设置创建人
							Method setMethod = classType.getMethod("setCreateBy", new Class[] { java.lang.String.class });
	        				setMethod.invoke(object[0], new Object[] { loginUserPojo.getUserAccount()});
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}
					try {//设置isEnable为1
						Method setMethod = classType.getMethod("setIsEnable", new Class[] { java.lang.Integer.class });
        				setMethod.invoke(object[0], new Object[] { Constants.IS_ENABLE_TRUE });
					} catch (Exception e) {
						//e.printStackTrace();
					}
    			}else if(object[0].getClass().getName().startsWith("java.util.HashMap")){
    				Map data = (java.util.HashMap)object[0];
    				//设置创建时间
    				data.put("createDt",  new Date() );
    				//设置创建人
    				data.put("createBy",  loginUserPojo.getUserAccount());
    				//设置isEnable为1
    				data.put("isEnable", Constants.IS_ENABLE_TRUE );
    			}
    		}else if(methodName.startsWith("update")){
    			if(object[0].getClass().getName().startsWith("com.insight.wisehealth")){
    				Class<?> classType = object[0].getClass();
					try {//设置修改时间
						Method setMethod = classType.getMethod("setUpdateDt", new Class[] {  java.util.Date.class });
        				setMethod.invoke(object[0], new Object[] { new Date() });
					} catch (Exception e) {
						//e.printStackTrace();
					}
					if(loginUserPojo!=null){
						try {//设置修改人
							Method setMethod = classType.getMethod("setUpdateBy", new Class[] {java.lang.String.class});
	        				setMethod.invoke(object[0], new Object[] { loginUserPojo.getUserAccount()});
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}
    			}else if(object[0].getClass().getName().startsWith("java.util.HashMap")){
    				Map data = (java.util.HashMap)object[0];
    				//设置修改时间
    				data.put("updateDt",  new Date() );
    				//设置修改人
    				data.put("updateBy",  loginUserPojo.getUserAccount());
    			}
    		}
    	}
    }
    @Before("pointcutsave()")
    public void doSaveBefore(JoinPoint point) {
    	//获取参数值
    	Object[] object = point.getArgs();
    	String methodName = point.getSignature().getName();      
    	LoginUserPojo loginUserPojo = SessionUser.getCurrentUser();
    	if(object!=null&&object.length>0){
    		if(methodName.startsWith("save")){
    			Class<?> classType = object[0].getClass();
    			if(object[0].getClass().getName().startsWith("com.insight.wisehealth")){
    				try {//设置isEnable为1
						Method setMethod = classType.getMethod("setIsEnable", new Class[] { java.lang.Integer.class });
        				setMethod.invoke(object[0], new Object[] { Constants.IS_ENABLE_TRUE });
					} catch (Exception e) {
						//e.printStackTrace();
					}
    			}else if(object[0].getClass().getName().startsWith("java.util.HashMap")){
    				Map data = (java.util.HashMap)object[0];
    				//设置修改时间
    				data.put("isEnable", Constants.IS_ENABLE_TRUE );
    			}
    		}
    	}
    }
    @AfterReturning("pointcut()")
    public void doAfterReturning() {
        System.out.println("doAfterReturning advice");
    }

    @After("pointcut()")
    public void doAfter() {
        System.out.println("doAfter advice");
    }

    @AfterThrowing("pointcut()")
    public void doAfterThrowing() {
        System.out.println("doAfterThrowing advice");
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("doAround advice start");
        Object result = pjp.proceed();
        System.out.println("doAround advice end");
        return result;
    }

}