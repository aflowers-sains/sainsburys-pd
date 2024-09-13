package uk.co.sainsburys.dbaudit

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(id = "dbaudit", topics = ["\${kafka.topic.name}"])
class ParameterNotificationListenerKafka(val auditRepository: AuditRepository ) {
    @KafkaHandler
    fun parameterNotificationListenerKafka(parameter: String?) {
        val auditEntity: AuditEntity = AuditEntity(null, "We modified a parameter: ${parameter}", null)
        auditRepository.save(auditEntity)
    }
}
