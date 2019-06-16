package com.insight.core.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.RequestUtil;
import com.insight.core.util.StringUtil;

public class VteRequestFileter extends CharacterEncodingFilter {

	public VteRequestFileter() {
		super();
	}

	public VteRequestFileter(String encoding) {
		super(encoding);
	}

	public VteRequestFileter(String encoding, boolean forceEncoding) {
		super(encoding, forceEncoding, forceEncoding);
	}

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String encoding = getEncoding();
		if (encoding != null) {
			if ((isForceRequestEncoding())
					|| (request.getCharacterEncoding() == null)) {
				request.setCharacterEncoding(encoding);
			}
			if (isForceResponseEncoding()) {
				response.setCharacterEncoding(encoding);
			}
		}
		//setCorsHeader(response, request);
		if (request.getMethod().equals("POST")) {
			StringBuffer requestURL = request.getRequestURL();
			if(!requestURL.toString().contains("/services/")){
				String jsonObjectData = RequestUtil.getJsonObjectData(request);
				if (!StringUtil.isEmpty(jsonObjectData)) {
					Map requestMap = JsonUtil.getMapFromJson(jsonObjectData);
					VteRequestWrapper requestWrapper = new VteRequestWrapper(
							(HttpServletRequest) request);
					for (Object key : requestMap.keySet()) {
						requestWrapper.addParameter(key.toString(),
								requestMap.get(key));
						// filterChain.doFilter(requestWrapper,
						// httpServletResponse);
					}
					System.out.println(jsonObjectData);
					filterChain.doFilter(requestWrapper, response);
				}else{
					filterChain.doFilter(request, response);
				}
			}else{
				setCorsHeader(response, request);
				filterChain.doFilter(request, response);
			}
		}else{
			filterChain.doFilter(request, response);
		}
		
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

}