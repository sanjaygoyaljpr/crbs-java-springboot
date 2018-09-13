package com.sanjaygoyaljpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class CrbsApplication {
	
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		
		applicationContext = SpringApplication.run(CrbsApplication.class, args);
		
		//displayAllBeans();
		
		//MessageSource messages = applicationContext.getBean(MessageSource.class);
		
		
		//String errorMessage = messages.getMessage("message.badCredentials", null, Locale.ENGLISH);
		//System.out.println(errorMessage);
	}
	
	public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}