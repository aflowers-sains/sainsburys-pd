package uk.co.sainsburys.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@RestController
public class DynamoDBController {
  private final DynamoDbTemplate dynamoDbTemplate;

  public DynamoDBController(DynamoDbTemplate dynamoDbTemplate) {
    this.dynamoDbTemplate = dynamoDbTemplate;
  }

  @GetMapping("/list")
  public ResponseEntity<List<SectorsStores>> retrieveAllSectorsAndStores() {
    return ResponseEntity.ok(dynamoDbTemplate.scanAll(SectorsStores.class).items().stream().toList());
  }

  @GetMapping("/list/{sector}")
  public ResponseEntity<List<SectorsStores>> retrieveAllSectorsAndStoresBySector(@PathVariable("sector") String sector) {
    QueryEnhancedRequest queryRequest =
        QueryEnhancedRequest.builder()
            .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(sector).build()))
            .scanIndexForward(true)
            .build();

    return ResponseEntity.ok(dynamoDbTemplate.query(queryRequest, SectorsStores.class).items().stream().toList());
  }

  @GetMapping("/stores/{sector}")
  public ResponseEntity<List<SectorsStores>> retrieveStoresForSector(@PathVariable("sector") String sector,
      @RequestParam(value = "from", defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate from) {
    QueryEnhancedRequest queryRequest =
        QueryEnhancedRequest.builder()
            .queryConditional(QueryConditional.sortLessThanOrEqualTo(Key.builder().partitionValue(sector).sortValue(DateTimeFormatter.ISO_DATE.format(from)).build()))
            .scanIndexForward(false)
            .build();

    return ResponseEntity.ok(dynamoDbTemplate.query(queryRequest, SectorsStores.class).items().stream().findFirst().stream().toList());
  }

  @PostMapping("/stores")
  public ResponseEntity<Void> addStoreSectorMapping(@RequestBody SectorsStores store) {
    dynamoDbTemplate.save(store);

    return ResponseEntity.ok().build();
  }
}
