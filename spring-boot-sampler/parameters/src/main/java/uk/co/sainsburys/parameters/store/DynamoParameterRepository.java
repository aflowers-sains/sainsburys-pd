package uk.co.sainsburys.parameters.store;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface DynamoParameterRepository extends CrudRepository<DynamoParameter, String> {
    List<DynamoParameter> findByName(String name);
}
