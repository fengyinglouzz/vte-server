package com.insight.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

import org.apache.commons.beanutils.PropertyUtils;
/**
 * 该策略为 如果bean中没有Json对应的field,不会抛出异常
 * 
 * 即:json中的 field 大于等于 bean中的field
 * @author Frank
 * @version 1.0
 */
public class JsonPropertySetStrategy extends PropertySetStrategy {
	private static JsonPropertySetStrategy instance;
	private JsonPropertySetStrategy() {
		
	}
	
	/**
	 * 构造方法
	 * @return JsonPropertySetStrategy
	 */
	public static JsonPropertySetStrategy getInstance(){
		if(instance == null){
			instance = new JsonPropertySetStrategy();
		}
		return instance; 
	}
	
	/**
	 * @author Frank
	 * @param bean bean对象
	 * @param key key建
	 * @param value 值
	 */
	public void setProperty(Object bean, String key, Object value ) throws JSONException {
        setProperty( bean, key, value, new JsonConfig());
     }
     
	/**
	 * @author Frank
	 * @param bean bean对象
	 * @param key key建
	 * @param value 值 
	 * @param jsonConfig  JsonConfig对象
	 */
     public void setProperty( Object bean, String key, Object value, JsonConfig jsonConfig ) throws JSONException {
        if( bean instanceof Map ){
           ((Map) bean).put( key, value );
        } else {
           if( !jsonConfig.isIgnorePublicFields() ) {
              try {
                 Field field = bean.getClass().getField( key );
                 if( field != null ) field.set( bean, value );
              } catch( Exception e ){
                 _setProperty( bean, key, value );
              }
           } else {
        	   if ((jsonConfig.isHandleJettisonEmptyElement()) && (value==null||"".equals(value))) {//值被设置成null时
        		   try {
    				   String propertyType =  getBeanPropertyType(bean,key);
    				   if(propertyType!=null&&propertyType.indexOf("String")>0){//如果类型是 String 把"" 设置给这个属性
    					    _setProperty( bean, key, "" );
    				   }else{
    					   _setProperty( bean, key, value );
    				   }
    				} catch (SecurityException e) {
    					e.printStackTrace();
    				}
        	   }else{
        		   _setProperty( bean, key, value );
        	   }
              
           }
        }
     }
     
     
     private String getBeanPropertyType( Object bean, String key){
    	 String type = "";
    	 Field[] fs = bean.getClass().getDeclaredFields();  
         for(int i = 0 ; i < fs.length; i++){  
             Field f = fs[i];  
             f.setAccessible(true); //设置些属性是可以访问的  
             if(key!=null&&key.equals(f.getName())){
            	 type = f.getType().toString();//得到此属性的类型  
            	 break;
             }
         }  
         return type;
     }
     
     private void _setProperty( Object bean, String key, Object value ) {
        try {
           PropertyUtils.setSimpleProperty( bean, key, value );
        } catch( Exception e ) {
        }
     }

}
