package uk.co.sainsburys.dbaudit

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/sainsburys/audit"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DBAuditController(private val auditRepository: AuditRepository) {

    @GetMapping
    fun retrieveAudits(): MutableIterable<AuditEntity> {
        return auditRepository.findAll()
    }
}