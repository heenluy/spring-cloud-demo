package me.henriqueluiz.second;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class SecondApplication {

	@Value("${service.instance.name}")
	private String name;

	public static void main(String[] args) {
		SpringApplication.run(SecondApplication.class, args);
	}

	@RequestMapping("/")
	public String message() {
		return "Hello from " + name + ":)";
	}

}
