package com.training.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Resources controlled
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", "file:/Java/pilot-project/")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS).cachePublic());
	}
}