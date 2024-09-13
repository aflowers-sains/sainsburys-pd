package uk.co.sainsburys.clients;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    private int id;
    private String name;
    private String address;
    private LocalDate dateOfBirth;

    // The ids of the policies this client has - we don't store the policies - that's in the policy service
    private List<String> policies;
}
