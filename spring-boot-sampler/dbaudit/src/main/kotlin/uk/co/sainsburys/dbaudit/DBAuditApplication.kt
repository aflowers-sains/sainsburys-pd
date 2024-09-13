package uk.co.sainsburys.dbaudit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DBAuditApplication

fun main(args: Array<String>) {
	runApplication<DBAuditApplication>(*args)
}
