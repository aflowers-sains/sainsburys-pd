package uk.co.sainsburys.parameters;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamoDBRepositories
public class ParametersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParametersApplication.class, args);
	}

}
