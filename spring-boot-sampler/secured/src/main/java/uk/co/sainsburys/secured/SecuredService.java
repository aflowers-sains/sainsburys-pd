package uk.co.sainsburys.secured;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import net.datafaker.Faker;

@Service
public class SecuredService {
    private final List<String> licensePlates = new ArrayList<>();

    public SecuredService() {
        Faker faker = new Faker(Locale.UK);

        for(int id = 0; id < 10; ++id) {
            licensePlates.add(faker.vehicle().licensePlate());
        }
    }

    @PreAuthorize("hasRole('ROLE_RETRIEVER')")
    public List<String> retrieveLicensePlates() {
        return licensePlates;
    }
}
