package pl.edu.agh.two.mud.server.configuration;

import org.springframework.context.support.*;

public class ApplicationContext {

	private static ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	public static Object getBean(String bean) {
		return applicationContext.getBean(bean);
	}

	public static ClassPathXmlApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
