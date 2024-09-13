package uk.co.sainsburys.policies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import net.datafaker.Faker;

@Service
public class PolicyService {
    private final List<Policy> policies = new ArrayList<>();

    public PolicyService() {
        Faker faker = new Faker(Locale.UK);

        for(int id = 0; id < 10; ++id) {
            policies.add(new Policy(
                            Integer.toString(id),
                            faker.date().past(300, TimeUnit.DAYS),
                            faker.number().numberBetween(1, 100),
                            faker.vehicle().licensePlate(),
                            PolicyType.CAR));
        }
    }

    public Optional<Policy> retrievePolicy(String id) {
        return policies.stream().filter(policy -> policy.id().equals(id)).findFirst();
    }

    public Map<String, List<Policy>> retrievePoliciesWithType() {
        Map<String, List<Policy>> policiesByType = new HashMap<>();

        policies.forEach(p -> {
            switch(p.policyType()) {
                case CAR -> policiesByType.computeIfAbsent("CAR", k -> new ArrayList<>()).add(p);
                case HOME -> policiesByType.computeIfAbsent("HOME", k -> new ArrayList<>()).add(p);
                case PET -> policiesByType.computeIfAbsent("PET", k -> new ArrayList<>()).add(p);
            }
        });

        return policiesByType;
    }
}
