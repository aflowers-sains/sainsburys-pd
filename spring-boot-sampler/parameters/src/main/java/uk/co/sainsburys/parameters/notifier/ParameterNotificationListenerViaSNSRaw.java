package uk.co.sainsburys.parameters.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import uk.co.sainsburys.parameters.Parameter;

@Component
public class ParameterNotificationListenerViaSNSRaw {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterNotificationListenerViaSNSRaw.class);

    @SqsListener(value = "${sqs.queue.name.sns.raw}", maxMessagesPerPoll = "1")
    public void parameterNotificationListenerSQSViaRawSQS(Parameter parameter) {
        LOGGER.info("Parameter update via SQS/SNS Raw - {} = {}", parameter.name(), parameter.value());
    }
}

