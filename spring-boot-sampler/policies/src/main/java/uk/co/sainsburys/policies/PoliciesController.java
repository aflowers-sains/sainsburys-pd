package uk.co.sainsburys.policies;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping( value ="/sainsburys/policies", produces = APPLICATION_JSON_VALUE)
public class PoliciesController {
    private static final Logger logger = LoggerFactory.getLogger(PoliciesController.class);

    private final PolicyService policyService;

    private RestTemplate restTemplate;

    public PoliciesController(PolicyService policyService, RestTemplate restTemplate) {
        this.policyService = policyService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public ResponseEntity<List<Policy>> retrievePoliciesByQuery() {
        // for now it's a bad request to ask for all policies
        // TODO : need to decide on the query params we can pass to limit what we search for

        // simulate a call to the parameters service to see if we support getting all policies
        String parameters = restTemplate.getForObject("http://localhost:8667/sainsburys/parameters/", String.class);

        logger.info("Read some parameters, {}", parameters);

        logger.info("We don't support asking for all policies");

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> retrievePolicy(@PathVariable("id") String id) {
        Optional<Policy> policy = policyService.retrievePolicy(id);

        return policy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
