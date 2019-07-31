package com.insight.core.config;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.insight.core.common.ResponseStateConstants;
import com.insight.core.util.JsonUtil;
import com.insight.wisehealth.vte.common.SessionUser;
import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;

/**
 * Created by sang on 17-3-10.
 */
public class LoginInterceptors implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        setCorsHeader(httpServletResponse, httpServletRequest);
    	if(httpServletRequest.getMethod().equals("OPTIONS")){
    		 return true;
    	}
        if (!requestURL.toString().contains("system/login")&&!requestURL.toString().contains("system/logout")&&!requestURL.toString().contains("system/sso")) {
            LoginUserPojo loginUserPojo = (LoginUserPojo) httpServletRequest.getSession().getAttribute("loginUserPojo");
            if (loginUserPojo == null) {
            	httpServletResponse.setContentType("application/json;charset=UTF-8");
            	httpServletResponse.setStatus(ResponseStateConstants.LOGIN_OUT_TIME);
				PrintWriter out = httpServletResponse.getWriter();
				out.print("{\"status\":\""+ResponseStateConstants.LOGIN_OUT_TIME+"\"}");
                return false;
            } else {
            	//查看是否有访问权限
            	boolean allowFlag = false;
            	if(requestURL.toString().contains("systemDict/queryAllDict") || requestURL.toString().contains("exportAll")){
            		allowFlag =  true;
            	}else{
            		List<String> allowUrlList = loginUserPojo.getAllowUrlList();
            		if(allowUrlList!=null&&allowUrlList.size()>0){
                		for(String allowurl:allowUrlList){
                			if(requestURL.toString().contains(allowurl)){
                				allowFlag =  true;
                			}
                		}
                	}
            	}
            	if(allowFlag){
            		httpServletResponse.setHeader("loginStatus","1");
                	SessionUser.setCurrentUser(loginUserPojo);
                    return true;
            	}else{
                	httpServletResponse.setContentType("application/json;charset=UTF-8");
                	httpServletResponse.setStatus(ResponseStateConstants.NO_REQUEST_POWER);
    				PrintWriter out = httpServletResponse.getWriter();
    				out.print("{\"status\":\""+ResponseStateConstants.NO_REQUEST_POWER+"\"}");
                    return false;
                
            	}
            }
        }else{
        	httpServletResponse.setHeader("loginStatus","1");
            return true;
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
    
    /**
     * 设置跨域响应header
     * @param res
     */
    public  void setCorsHeader(HttpServletResponse res,HttpServletRequest request ){
    	if(request.getHeader("Origin") == null){
    		res.setHeader("Access-Control-Allow-Origin", "http://localhost:8002");	
    	}else{
    		 res.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    	}
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//允许跨域的请求方式
        res.setHeader("Access-Control-Max-Age", "3600");//预检请求的间隔时间
        res.setHeader("Access-Control-Allow-Headers", "content-type,Content-Type, Access-Control-Allow-Headers, Authorization, Access-Token");//允许跨域请求携带的请求头
        res.setHeader("Access-Control-Allow-Credentials","true");//若要返回cookie、携带seesion等信息则将此项设置我true
    }
    
    private boolean findUserPower(String url,LoginUserPojo loginUserPojo){
    	boolean flag = false;
    	
    	return flag;
    }

}
