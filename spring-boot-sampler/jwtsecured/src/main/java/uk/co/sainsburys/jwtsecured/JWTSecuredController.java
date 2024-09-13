package uk.co.sainsburys.jwtsecured;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value ="/sainsburys/jwtsecured", produces = APPLICATION_JSON_VALUE)
public class JWTSecuredController {
    @GetMapping("/")
    public ResponseEntity<String> retrieveLicensePlates(Authentication authentication) {
        return ResponseEntity.ok("Logged in as :: " + authentication.getName());
    }
}
