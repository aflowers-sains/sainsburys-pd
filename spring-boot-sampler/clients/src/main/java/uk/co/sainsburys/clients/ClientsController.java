package uk.co.sainsburys.clients;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.co.sainsburys.clients.service.ClientsService;

@RestController()
@RequestMapping( value ="/sainsburys/clients", produces = APPLICATION_JSON_VALUE)
public class ClientsController {
    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> retrieveClients() {
        return ResponseEntity.ok(clientsService.retrieveClients());
    }

    @PostMapping("/")
    public ResponseEntity<Integer> addClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientsService.addClient(client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> retrieveClient(@PathVariable("id") int id) {
        Optional<Client> client = clientsService.retrieveClient(id);

        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
