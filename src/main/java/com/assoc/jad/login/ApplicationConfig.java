package com.assoc.jad.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//import com.assoc.jad.loadbalancer.lbinstance.free.CreateLoadBalancerObj;
//import com.assoc.jad.loadbalancer.lbinstance.free.LoadBalancerObj;
//import com.assoc.jad.loadbalancer.lbinstance.free.WebSocketClient;

@Component
public class ApplicationConfig implements CommandLineRunner {
	
	@Value("${server.port}")
	private String serverPort;
	@Value("${spring.data.rest.base-path}")
	private String service;
	@Value("${server.ssl.key-store-type}")
	private String ssttype;

	@Override
	public void run(String... args) throws Exception {
////		CreateLoadBalancerObj createLoadBalancerObj = new CreateLoadBalancerObj();
////		LoadBalancerObj lbObj = new LoadBalancerObj();
//		lbObj.setInstancePort(Integer.valueOf(serverPort));
//		String wrkstr = service;
//		if (service.startsWith("/")) wrkstr = service.substring(1);
//		lbObj.setInstance(wrkstr);
//		
//		String schema = "hhtp";
//		if (ssttype != null) schema = "https";
//		lbObj.setSchema(schema);
//		
//		String sessionid = "NOSESSIONID"+service;
////		lbObj = createLoadBalancerObj.bldLoadBalancerObj(sessionid, lbObj);
////		new Thread(new WebSocketClient(lbObj,sessionid),this.getClass().getSimpleName()).start();
	}
}
