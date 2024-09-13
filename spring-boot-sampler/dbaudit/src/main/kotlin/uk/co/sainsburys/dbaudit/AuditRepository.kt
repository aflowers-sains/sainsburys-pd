package uk.co.sainsburys.dbaudit

import org.springframework.data.repository.CrudRepository

interface AuditRepository: CrudRepository<AuditEntity, Int> {
}