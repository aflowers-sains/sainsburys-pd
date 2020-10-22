package com.sainsburys.psr.fcrs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.slf4j.LoggerFactory
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class
)
@AutoConfigureMockMvc
@ActiveProfiles(["test"])
abstract class IntegrationTestBase extends Specification {
    @Autowired
    protected MockMvc mvc

    protected static ListAppender setupLogAppender(Class<?> className) {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>()
        Logger logger = (Logger) LoggerFactory.getLogger(className)
        listAppender.start()
        logger.addAppender(listAppender)
        return listAppender
    }

    List<String> filterLogMessages(ListAppender<ILoggingEvent> listAppender, Level level) {
        def filtered = filterLogItems(listAppender, level)

        return filtered*.getMessage()
    }

    def filterLogItems(ListAppender<ILoggingEvent> listAppender, Level level) {
        return listAppender.list.findAll() { it.getLevel() == level }
    }

    def filterLogItems(ListAppender<ILoggingEvent> listAppender, Level level, String message) {
        return filterLogItems(listAppender, level).findAll() { it.getMessage() == message }
    }
}
