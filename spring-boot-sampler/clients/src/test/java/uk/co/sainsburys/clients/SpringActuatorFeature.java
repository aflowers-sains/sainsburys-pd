package uk.co.sainsburys.clients;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.intuit.karate.junit5.Karate;

@SpringBootTest(
        classes = {ClientsApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "eureka.client.enabled:false")
public class SpringActuatorFeature {
    @LocalServerPort private String localServerPort;

    @Karate.Test
    public Karate actuatorResourceIsAvailable() {
        return karateSzenario("actuator resource is available");
    }

    @Karate.Test
    public Karate healthResourceStatusIsUp() {
        return karateSzenario("health resource status is \"up\"");
    }

    private Karate karateSzenario(String s) {
        return Karate.run()
                .scenarioName(s)
                .relativeTo(getClass())
                .systemProperty("karate.port", localServerPort)
                .karateEnv("dev");
    }
}
