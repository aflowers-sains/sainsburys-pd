package uk.co.sainsburys.clients.service;

import java.util.List;
import java.util.Optional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import uk.co.sainsburys.clients.Client;

class ObservedClientsServiceImpl implements ClientsService {
    private final ClientsService delegate;
    private final ObservationRegistry observationRegistry;

    public ObservedClientsServiceImpl(ClientsService delegate, ObservationRegistry observationRegistry) {
        this.delegate = delegate;
        this.observationRegistry = observationRegistry;
    }

    @Override
    public List<Client> retrieveClients() {
        return Observation.createNotStarted("clients.retrieve", observationRegistry)
                .observe(delegate::retrieveClients);
    }

    @Override
    public Optional<Client> retrieveClient(int id) {
        return Observation.createNotStarted("client.retrieve", observationRegistry)
                .lowCardinalityKeyValue("client.id", String.valueOf(id))
                .observe(() -> delegate.retrieveClient(id));
    }

    @SuppressWarnings("java:S2259")
    @Override
    public int addClient(Client client) {
        return Observation.createNotStarted("client.add", observationRegistry)
                .lowCardinalityKeyValue("client.name", client.getName())
                .lowCardinalityKeyValue("client.num.policies", String.valueOf(client.getPolicies().isEmpty() ? 0 : client.getPolicies().size()))
                .observe(() -> delegate.addClient(client));
    }
}
