package me.henriqueluiz.configclientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConfigClientAppApplication {

	@Autowired
	private ConfigClientConfiguration properties;

	@Value("${some.other.property}")
	private String someOtherProperty;

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientAppApplication.class, args);
	}

	@RequestMapping("/")
	public String printConfig() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(properties.getProperty());
		stringBuilder.append(" || ");
		stringBuilder.append(someOtherProperty);

		return stringBuilder.toString();
	}

}
