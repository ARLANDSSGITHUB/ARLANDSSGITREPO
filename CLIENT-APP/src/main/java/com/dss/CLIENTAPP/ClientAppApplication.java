package com.dss.CLIENTAPP;

import com.dss.CLIENTAPP.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class ClientAppApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ClientAppApplication.class, args);
		ClientService clientService = applicationContext.getBean("clientService",ClientService.class);
		System.out.println(clientService.getServiceInstance());
	}

}
