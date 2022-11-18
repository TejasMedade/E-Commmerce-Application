package com.masai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class E_Commerce {

	public static void main(String[] args) {
		SpringApplication.run(E_Commerce.class, args);
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource ms) {

		LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
		lvfb.setValidationMessageSource(ms);

		return lvfb;

	}

}
