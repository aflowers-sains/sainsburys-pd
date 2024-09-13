package uk.co.sainsburys.parameters;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping( value ="/sainsburys/parameters", produces = APPLICATION_JSON_VALUE)
public class ParametersController {
    private final ParametersService parametersService;

    public ParametersController(ParametersService parametersService) {
        this.parametersService = parametersService;
    }

    @GetMapping("/")
    ResponseEntity<List<Parameter>> retrieveParameters() {
        return ResponseEntity.ok(parametersService.retrieveParameters());
    }

    @GetMapping("/{parameterName}")
    ResponseEntity<Parameter> retrieveParameter(@PathVariable("parameterName") String parameterName) {
        return ResponseEntity.of(parametersService.retrieveParameter(parameterName));
    }

    @PostMapping("/")
    ResponseEntity<String> addParameter(@RequestBody Parameter parameter) {
        parametersService.addParameter(parameter);
        return ResponseEntity.ok("added");
    }
}
