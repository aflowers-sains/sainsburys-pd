package uk.co.sainsburys.parameters.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class KafkaConfig {
    private KafkaTemplate kafkaTemplate;

    public KafkaConfig(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public NewTopic topic(@Value("${kafka.topic.name}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }

    @PostConstruct
    public void setKafkaTemplateObservable() {
        kafkaTemplate.setObservationEnabled(true);
    }
}
