package com.sainsburys.pd.client

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification
import spock.lang.Unroll

class ClientPactTestSpec extends Specification {
    private Client client
    private PactBuilder provider

    def setup() {
        client = new Client('http://localhost:1234')

        provider = new PactBuilder()
        provider {
            serviceConsumer 'Addition_Consumer'
            hasPactWith 'Addition_Provider'
            port 1234
        }
    }

    def 'Pact with the addition service provider'() {
        given:
        provider {
            uponReceiving('a request for adding numbers')
            withAttributes(method: 'get', path: '/add', query: [first: regexp('^[0-9]\\d*$', "1"), second: regexp('^[0-9]\\d*$', "2")])
            willRespondWith(status: 200)
            withBody {
                answer integer(25)
            }
        }

        when:
        def result
        PactVerificationResult pactResult = provider.runTest {
            result = client.addNumbers(1, 2)
        }

        then:
        pactResult == PactVerificationResult.Ok.INSTANCE
        result == 25
    }

    @Unroll
    def 'handles negative numbers'(int first, int second) {
        given:
        provider {
            uponReceiving('a request for adding negative numbers')
            withAttributes(method: 'get', path: '/add', query: [first: regexp('^-*[0-9]\\d*$', "${first}".toString()), second: regexp('^-*[0-9]\\d*$', "${second}".toString())])
            willRespondWith(status: 400, body: '{"error": "cannot do negative numbers"}')
        }

        when:
        PactVerificationResult pactResult = provider.runTest {
            client.addNumbers(first, second)
        }

        then:
        pactResult == PactVerificationResult.Ok.INSTANCE

        where:
        first | second
        -12   | 13
        12    | -13
    }
}