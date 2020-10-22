package com.sainsburys.psr.fcrs.service

import com.sainsburys.psr.fcrs.service.flrs.FlrsClient
import com.sainsburys.psr.fcrs.service.flrs.dto.*
import feign.FeignException
import spock.lang.Specification

class FlrsServiceSpec extends Specification {

    def FLRS_ID = "Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm"
    def STORE_ID = "2320"
    def STORE_NAME = "Herne Bay"

    FlrsClient flrsClient = Mock(FlrsClient)
    FlrsService flrsService = new FlrsService(flrsClient)

    def "Test that retrieves store id from FLRS"() {
        given: "a flrs request"
        def flrsRequest = new GraphQLRequest()
        flrsRequest.query = "query(\$id: ID) { fulfilmentLocation(filter:{id: \$id}) " +
                "{ ... on ClickAndCollect { attributes { fulfilledBy { ... on SainsburysStore { code name }}}}}}";
        flrsRequest.variables = [id: FLRS_ID]

        and: "a valid flrs response"
        def flrsResponse = new GraphQLResponse()
        flrsResponse.data = new Data()
        flrsResponse.data.fulfilmentLocation = new FulfilmentLocation()
        flrsResponse.data.fulfilmentLocation.attributes = new ClickAndCollectAttributes()
        flrsResponse.data.fulfilmentLocation.attributes.fulfilledBy = new FulfilledBy(STORE_ID, "Herne Bay")

        and: "flrs client set up to return the right response for the right request"
        1 * flrsClient.post(flrsRequest) >> flrsResponse

        when: "flrs id is passed to the service"
        def output = flrsService.getFullfilledByForFlrsId(FLRS_ID)

        then: "the store code of 2320 is returned"
        output.get().code == STORE_ID

        and: "the required store name is returned"
        output.get().name == STORE_NAME
    }

    def "If FLRS is not available we throw the exception"() {
        given: "flrs client set up to simulate inability to communicate"
        flrsClient.post(_) >> { throw Mock(FeignException) }

        when: "we call out to flrs"
        flrsService.getFullfilledByForFlrsId(FLRS_ID)

        then: "expect a cannot communicate with flrs exception to be thrown"
        thrown FeignException
    }

    def "If FLRS does not know of the flrs id we pass in return an empty optional"() {
        given: "flrs client set up to return response indicating could not find the location"
        def flrsResponse = new GraphQLResponse()
        flrsResponse.data = new Data()
        flrsResponse.data.fulfilmentLocation = null

        and: "flrs client return response"
        flrsClient.post(_) >> flrsResponse

        when: "we call out to flrs"
        Optional<FulfilledBy> output = flrsService.getFullfilledByForFlrsId(FLRS_ID)

        then: "expect an empty optional"
        output.isPresent() == false
    }
}
