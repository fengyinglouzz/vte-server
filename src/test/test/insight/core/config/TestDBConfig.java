package test.insight.core.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.insight.core.config.DBConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class TestDBConfig {
	
	protected final Log logger = LogFactory.getLog(getClass());
 
	@Test
	public void checkTest(){
		System.out.println("---------");
		if (logger.isDebugEnabled()) {
		    logger.debug("true");
		}
		System.out.println("---------");
	}

}
