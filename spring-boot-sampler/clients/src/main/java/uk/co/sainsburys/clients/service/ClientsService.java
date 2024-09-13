package uk.co.sainsburys.clients.service;

import java.util.List;
import java.util.Optional;

import uk.co.sainsburys.clients.Client;

public interface ClientsService {
    List<Client> retrieveClients();

    Optional<Client> retrieveClient(int id);

    int addClient(Client client);
}
