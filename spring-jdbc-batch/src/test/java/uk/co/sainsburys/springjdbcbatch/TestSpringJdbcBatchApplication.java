package uk.co.sainsburys.springjdbcbatch;

import org.springframework.boot.SpringApplication;

public class TestSpringJdbcBatchApplication {

  public static void main(String[] args) {
    SpringApplication.from(SpringJdbcBatchApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
