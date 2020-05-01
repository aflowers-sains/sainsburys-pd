package com.sainsburys.pd

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AppTest extends Specification {
    def "application starts with no issue"() {
        when: "the application starts up"

        then: "there are no errors"
            noExceptionThrown()
    }
}
