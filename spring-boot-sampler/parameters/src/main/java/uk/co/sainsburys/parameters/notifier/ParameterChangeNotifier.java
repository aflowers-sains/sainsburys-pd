package uk.co.sainsburys.parameters.notifier;

import io.awspring.cloud.sqs.operations.SendResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import uk.co.sainsburys.parameters.Parameter;

@Component
public class ParameterChangeNotifier {

    public enum NotificationType {
        ADDED,
        UPDATED,
        DELETED
    }

    private final String sqsQueue;
    private final SqsTemplate sqsTemplate;

    private final String snsDestination;
    private final SnsTemplate snsTemplate;

    private final String topicName;
    private final KafkaTemplate<String, Parameter> kafkaTemplate;

    public ParameterChangeNotifier(@Value("${sqs.queue.name}") String sqsQueue, SqsTemplate sqsTemplate, @Value("${sns.destination.name}") String snsDestination, SnsTemplate snsTemplate, @Value("${kafka.topic.name}") String topicName, KafkaTemplate<String, Parameter> kafkaTemplate) {
        this.sqsQueue = sqsQueue;
        this.sqsTemplate = sqsTemplate;
        this.snsDestination = snsDestination;
        this.snsTemplate = snsTemplate;
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notify(Parameter parameter, NotificationType notificationType) {
        SendResult<Parameter> result = sqsTemplate.send(sqsQueue, parameter);

        snsTemplate.sendNotification(snsDestination, parameter, null);

        kafkaTemplate.send(topicName, parameter.name(), parameter);
    }
}
