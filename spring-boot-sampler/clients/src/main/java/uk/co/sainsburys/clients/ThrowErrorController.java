package uk.co.sainsburys.clients;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping( value ="/sainsburys/error", produces = APPLICATION_JSON_VALUE)
public class ThrowErrorController {
    @GetMapping("/")
    public ResponseEntity<Void> throwAnException() {
        throw new RuntimeException("I have failed!!");
    }
}
