package uk.co.sainsburys.quotes.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import uk.co.sainsburys.parameters.Parameter;

@Component
public class ParameterNotificationListenerSQS {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterNotificationListenerSQS.class);

    @SqsListener(value = "${sqs.queue.name}", maxMessagesPerPoll = "1")
    public void parameterNotificationListenerSQS(Parameter parameter) {
        LOGGER.info("Parameter update via SQS - {} = {}", parameter.getName(), parameter.getValue());
    }
}
