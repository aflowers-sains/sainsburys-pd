package uk.co.sainsburys.serviceconnector;

import org.springframework.boot.SpringApplication;

public class TestServiceconnectorApplication {

  public static void main(String[] args) {
    SpringApplication.from(ServiceconnectorApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
