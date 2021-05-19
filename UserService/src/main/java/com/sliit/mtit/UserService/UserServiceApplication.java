package com.sliit.mtit.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("http://localhost:8082/api/v1/users/*"))
				.apis(RequestHandlerSelectors.basePackage("com.sliit.mtit"))
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"ABC's Online Business User RESTful API as microservice",
				"This microservice stores and retrieve ABC company customers",
				"1.0",
				"Only for ABC company customers and employees",
				new springfox.documentation.service.Contact("Asanka BL", "abc-biss.com", "abc@mail.com"),
				"API liecense",
				"https://abc-buy-online.com",
				Collections.emptyList()
		);
	}

}
