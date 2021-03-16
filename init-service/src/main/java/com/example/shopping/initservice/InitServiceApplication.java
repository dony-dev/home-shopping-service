package com.example.shopping.initservice;

import com.example.shopping.initservice.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/* */
//@RibbonClient(name = "product-service", configuration = RibbonConfiguration.class)
//@EnableEurekaClient
@EnableDiscoveryClient
public class InitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitServiceApplication.class, args);
	}

	/* */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
