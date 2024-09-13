package uk.co.sainsburys.policies.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import uk.co.sainsburys.parameters.Parameter;

@Component
@KafkaListener(id = "policies", topics = "${kafka.topic.name}")
public class ParameterNotificationListenerKafka {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterNotificationListenerKafka.class);
    @KafkaHandler
    public void parameterNotificationListenerKafka(Parameter parameter) {
        LOGGER.info("POLICIES: Parameter update via Kafka - {} = {}", parameter.getName(), parameter.getValue());
    }
}
