package com.insight.core.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Configuration
public class DBConfig {
	    public static String driver;
	    public static String url;
	    public static String username;
	    public static String password;
	    public static int initialSize;
	    public static int maxActive;
	    public static int maxIdle;
	    public static int minIdle;
	    public static int maxWait;
	    
		static{
			ResourceBundle rb= ResourceBundle.getBundle("jdbc");
			driver = rb.getString("db.driverClass");
			url = rb.getString("db.url");
			username = rb.getString("db.username");
			password = rb.getString("db.password");
			initialSize = Integer.parseInt(rb.getString("db.initialSize"));
			maxActive = Integer.parseInt(rb.getString("db.maxActive"));
			maxIdle = Integer.parseInt(rb.getString("db.maxIdle"));
			minIdle = Integer.parseInt(rb.getString("db.minIdle"));
			maxWait = Integer.parseInt(rb.getString("db.maxWait"));
		}
	
   
}
