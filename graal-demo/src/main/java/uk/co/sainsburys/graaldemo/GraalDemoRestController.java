package uk.co.sainsburys.graaldemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraalDemoRestController {
  @GetMapping(path = "/", produces = "text/plain")
  public String helloGraal() {
    return "Hello, GraalVM!";
  }
}
