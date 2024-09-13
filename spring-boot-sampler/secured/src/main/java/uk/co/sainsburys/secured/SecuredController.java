package uk.co.sainsburys.secured;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value ="/sainsburys/secured", produces = APPLICATION_JSON_VALUE)
public class SecuredController {
    private final SecuredService securedService;

    public SecuredController(SecuredService securedService) {
        this.securedService = securedService;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> retrieveLicensePlates() {
        // no security around the call to the controller over and above the defaults of having to be logged in
        // but the method call requires a specific role...so could fail
        return ResponseEntity.ok(securedService.retrieveLicensePlates());
    }
}
