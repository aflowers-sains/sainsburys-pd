package uk.co.sainsburys.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "parameters-service", property = "service.parameters.service-url")
})
class ClientsApplicationTests {

    @InjectWireMock("parameters-service")
    private WireMockServer parametersServiceWiremock;

//	@Test
//	void contextLoads() {
//		Assertions.assertTrue(true);
//	}

}
