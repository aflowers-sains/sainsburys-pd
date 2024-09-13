package uk.co.sainsburys.dbaudit

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp

@Entity
@Table(name = "audit", schema = "dbaudit")
class AuditEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var message: String,
    @CreationTimestamp
    var createdAt: Timestamp?
)