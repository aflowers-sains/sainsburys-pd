package uk.co.sainsburys.parameters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

@Configuration
public class DynamoDBConfig {
    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${spring.cloud.aws.region.static}") String region, @Value("${spring.cloud.aws.dynamodb.endpoint}") String endpoint,
            @Value("${spring.cloud.aws.credentials.access-key}") String accessKey, @Value("${spring.cloud.aws.credentials.secret-key}") String secretKey) {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpoint, region);

        return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).withCredentials(awsCredentialsProvider).build();
    }
}
