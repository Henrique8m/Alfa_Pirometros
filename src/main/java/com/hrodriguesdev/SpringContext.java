package com.hrodriguesdev;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {

	@Autowired
	static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.context = applicationContext;
		
	}

	public static ApplicationContext getAppContext() {
		return context;
	}

}
