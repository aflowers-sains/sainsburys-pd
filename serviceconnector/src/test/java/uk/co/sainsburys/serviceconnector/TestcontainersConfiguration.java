package uk.co.sainsburys.serviceconnector;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
  @Bean
  @ServiceConnection
  public PostgreSQLContainer serviceConnection() {
    return new PostgreSQLContainer(DockerImageName.parse("postgres:15.1"))
        .withUsername("testUser")
        .withPassword("testSecret")
        .withDatabaseName("testDatabase");
  }
}
