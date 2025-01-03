package uk.co.sainsburys.graaldemo;

import org.springframework.boot.SpringApplication;

public class TestGraalDemoApplication {

  public static void main(String[] args) {
    SpringApplication.from(GraalDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
