package uk.co.sainsburys.springjdbcbatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringJdbcBatchApplicationTests {
  @Autowired
  BatchImporter batchImporter;

  @Autowired
  JdbcTemplate jdbcTemplate;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:17-alpine"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Test
  void contextLoads() {
  }

  @Test
  void testBatchImport() {
    //    List<CustomerData> testData = List.of(
    //        new CustomerData("1", "SE1 9SG", "A", 100),
    //        new CustomerData("1", "SE1 1SG", "A", 100),
    //        new CustomerData("1", "SE1 6SG", "A", 100),
    //        new CustomerData("1", "SE1 9SG", "B", 101),
    //        new CustomerData("1", "SE1 7SG", "A", 100),
    //        new CustomerData("1", "SE1 8SG", "A", 100)
    //    );
    List<CustomerData> testData = new ArrayList<>();

    for(int i = 2; i < 500000; ++i) {
      testData.add(new CustomerData("" + i, "SE1 9SG", "A", 100));
    }

    long start = System.currentTimeMillis();

//    testData.forEach(batchImporter::individualImport);
    batchImporter.batchImport(testData);
    System.out.println("Took :: " + (System.currentTimeMillis() - start) + "ms");

    System.out.println("Updated count is :: " + jdbcTemplate.queryForObject("SELECT count(*) FROM customers", Integer.class));
//    jdbcTemplate.query("SELECT count(*) FROM customers", (rs, rowNum) ->
//        rs.getInt("1")
//    ).forEach(System.out::println);
  }
}
