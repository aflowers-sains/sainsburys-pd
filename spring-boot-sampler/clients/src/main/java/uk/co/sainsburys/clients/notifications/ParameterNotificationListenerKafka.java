package uk.co.sainsburys.clients.notifications;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import uk.co.sainsburys.clients.ParametersService;
import uk.co.sainsburys.parameters.Parameter;

@Component
@KafkaListener(id = "clients", topics = "${kafka.topic.name}")
public class ParameterNotificationListenerKafka {
    private ParametersService parametersService;

    public ParameterNotificationListenerKafka(ParametersService parametersService) {
        this.parametersService = parametersService;
    }

    @KafkaHandler
    public void parameterNotificationListenerKafka(Parameter parameter) {
        parametersService.updateParameter(parameter);
    }
}
