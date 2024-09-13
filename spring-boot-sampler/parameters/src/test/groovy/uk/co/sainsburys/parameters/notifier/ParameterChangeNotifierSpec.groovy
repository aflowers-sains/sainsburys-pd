package uk.co.sainsburys.parameters.notifier

import io.awspring.cloud.sqs.operations.SqsTemplate
import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.kafka.core.KafkaTemplate
import spock.lang.Specification
import uk.co.sainsburys.parameters.Parameter


class ParameterChangeNotifierSpec extends Specification {
    def "When a parameter change notification is sent ensure we send to all sinks"() {
        given:
            def parameter = new Parameter("param_name", "param_value")
            def sqsTemplateMock = Mock(SqsTemplate)
            def snsTemplateMock = Mock(SnsTemplate)
            def kafkaTemplateMock = Mock(KafkaTemplate)

            def testee = new ParameterChangeNotifier("sqsqueuename", sqsTemplateMock, "snstopic", snsTemplateMock,"kafkatopicname", kafkaTemplateMock)

        when:
            testee.notify(parameter, notificationType)

        then:
            1 * sqsTemplateMock.send("sqsqueuename", parameter)
            1 * snsTemplateMock.sendNotification("snstopic", parameter, _)
            1 * kafkaTemplateMock.send("kafkatopicname", "param_name", parameter)

        where:
        notificationType                                    | _
        ParameterChangeNotifier.NotificationType.ADDED      | _
        ParameterChangeNotifier.NotificationType.UPDATED    | _
        ParameterChangeNotifier.NotificationType.DELETED    | _
    }
}