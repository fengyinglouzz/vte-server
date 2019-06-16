package com.insight.core.config;

import java.io.IOException;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.insight")
public class MyMVCConfig extends WebMvcConfigurerAdapter{
	
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //两个*表示以/assets开始的任意层级的路径都可以访问得到图片，如<img src="../assets/img/1.png">
        //一个*表示只可以访问assets目录下的图片文件
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
        registry.addResourceHandler("/services/**").addResourceLocations("/services/");
        registry.addResourceHandler("/save/**").addResourceLocations("/save/");
        
    }
    
    
   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
          .allowedOrigins("*")
          .allowCredentials(true)
          .allowedMethods("*")
          .maxAge(3600);
    }*/

    
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
    	 dataSource.setDriverClassName(DBConfig.driver);
         dataSource.setUrl(DBConfig.url);
         dataSource.setUsername(DBConfig.username);
         dataSource.setPassword(DBConfig.password);
         dataSource.setInitialSize(DBConfig.initialSize);
         dataSource.setMaxActive(DBConfig.maxActive);
         dataSource.setMaxIdle(DBConfig.maxIdle);
         dataSource.setMinIdle(DBConfig.minIdle);
         dataSource.setMaxWait(DBConfig.maxWait);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }
    
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.insight.wisehealth.*.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptors());
    }
    
    @Bean
    public LoginInterceptors loginInterceptors() {
        return new LoginInterceptors();
    }
    
    @Bean 
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
    	 PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	 EhCacheManagerFactoryBean ehCacheManager = new EhCacheManagerFactoryBean();
         try {
        	 Resource[] configLocation =  resolver.getResources("classpath:ehcache.xml");
        	 if(configLocation!=null&&configLocation.length>=1){
        		 ehCacheManager.setConfigLocation(configLocation[0]);
        	 }
         } catch (IOException e) {
             e.printStackTrace();
         }
    	return ehCacheManager;
    }
    
    @Bean
    public EhCacheFactoryBean encache(){
    	EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
    	ehCacheFactoryBean.setCacheName("DEFAULT_CACHE");
    	ehCacheFactoryBean.setCacheManager((ehCacheManagerFactoryBean().getObject()));
		return ehCacheFactoryBean;
    }
    
    @Bean
    public DaoAspectAop daoAspectAop(){
    	DaoAspectAop daoAspectAop = new DaoAspectAop();
    	return daoAspectAop;
    }

}
