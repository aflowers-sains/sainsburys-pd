package uk.co.sainsburys.parameters.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class ParameterNotificationListenerViaSNS {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterNotificationListenerViaSNS.class);

    @SqsListener(value = "${sqs.queue.name.sns}", maxMessagesPerPoll = "1")
    public void parameterNotificationListenerSQSViaEncodedSNS(String payload) {
        LOGGER.info("Parameter update via SQS/SNS - {}", payload);
    }
}

