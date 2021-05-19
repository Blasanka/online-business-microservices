package com.sliit.mtit.PaymentService;

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
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/payments/*"))
				.apis(RequestHandlerSelectors.basePackage("com.sliit.mtit"))
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
			"ABC's Online Business Payment RESTful API as microservice",
			"This microservice communicate with auth microservice to authenticate and takes order details from Order microservice",
			"1.0",
			"Only for ABC company customers",
			new springfox.documentation.service.Contact("Asanka BL", "abc-biss.com", "abc@mail.com"),
			"API liecense",
			"https://abc-buy-online.com",
			Collections.emptyList()
		);
	}

}
