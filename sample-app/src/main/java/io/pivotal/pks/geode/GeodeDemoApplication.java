package io.pivotal.pks.geode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ImportResource("classpath:spring-gemfire.xml")
public class GeodeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeodeDemoApplication.class, args);
	}
}
