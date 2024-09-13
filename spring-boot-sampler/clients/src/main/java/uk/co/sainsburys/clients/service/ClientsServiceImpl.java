package uk.co.sainsburys.clients.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import net.datafaker.Faker;
import uk.co.sainsburys.clients.Client;

class ClientsServiceImpl implements ClientsService {

    private final List<Client> clients = new ArrayList<>();

    public ClientsServiceImpl() {
        Faker faker = new Faker(Locale.UK);

        for(int id = 0; id < 10; ++id) {
            String county = faker.expression("#{Address.county}");

            clients.add(Client.builder()
                    .id(id)
                    .name(faker.name().fullName())
                    .address(faker.address().streetAddress() + ", " + faker.address().city() + ", " + county + ", " + faker.address().postcode())
                    .dateOfBirth(faker.date().birthday(17, 65).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .policies(List.of(faker.number().digits(9), faker.number().digits(9), faker.number().digits(9)))
                    .build());
        }
    }

    @Override
    public List<Client> retrieveClients() {
        return clients;
    }

    @Override
    public Optional<Client> retrieveClient(int id) {
        return clients.stream().filter(client -> client.getId() == id).findFirst();
    }

    @Override
    public int addClient(Client client) {
        client.setId(clients.size() + 1);
        clients.add(client);

        return client.getId();
    }
}
