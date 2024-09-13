package uk.co.sainsburys.policies;

import java.util.Date;

enum PolicyType {
    CAR,
    HOME,
    PET
}

public record Policy (String id, Date start, int clientId, String registration, PolicyType policyType) {
}
