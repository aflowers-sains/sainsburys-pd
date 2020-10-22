package com.sainsburys.psr.fcrs;

class ApplicationSpecIT extends IntegrationTestBase {
    def "application starts"() {
        when: "the application starts up"
        then: "there are no errors"
        noExceptionThrown()
    }
}
