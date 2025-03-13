package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		SpringApplication application = new SpringApplication(Application.class);
		/*
			NONE : 웹 어플리케이션이 아님 (tomcat 동작하지 않음)
			SERVLET, (default) SpringMVC
			REACTIVE; WebFlux (async, non-blocking)
		*/
		//WebApplication의 타입 변경
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}

	@Bean
	public String hello(){
		return "Hello StringBoot";
	}

}
