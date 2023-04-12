package com.assoc.jad.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;

//import com.assoc.jad.loadbalancer.lbinstance.free.LoadBalancerFilter;

@SpringBootApplication
public class ServiceLoginApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceLoginApplication.class, args);
       
	}
//	@Bean
//	public FilterRegistrationBean<LoadBalancerFilter> loggingFilter(){
//	    FilterRegistrationBean<LoadBalancerFilter> registrationBean = new FilterRegistrationBean<LoadBalancerFilter>();
//	        
//	    registrationBean.setFilter(new LoadBalancerFilter());
//	    registrationBean.addUrlPatterns("/*");
//	    
//	    registrationBean.setOrder(2);
//	        
//	    return registrationBean; 
//	}
}
