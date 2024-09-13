package uk.co.sainsburys.parameters.store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import jakarta.annotation.PostConstruct;
import uk.co.sainsburys.parameters.Parameter;

@Component
public class ParameterStore {
    private final DynamoParameterRepository dynamoParameterRepository;

    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper mapper;

    public ParameterStore(DynamoParameterRepository dynamoParameterRepository, AmazonDynamoDB amazonDynamoDB, DynamoDBMapper mapper) {
        this.dynamoParameterRepository = dynamoParameterRepository;
        this.amazonDynamoDB = amazonDynamoDB;
        this.mapper = mapper;
    }
    @PostConstruct
    public void initialiseDB() throws InterruptedException {
        CreateTableRequest ctr = mapper.generateCreateTableRequest(DynamoParameter.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
        TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());

        if(dynamoParameterRepository.count() == 0) {
            for( int i = 0; i < 32; ++i) {
                DynamoParameter dynamoParameter = new DynamoParameter();

                dynamoParameter.setId(UUID.randomUUID());
                dynamoParameter.setName("name::" + i);
                dynamoParameter.setValue("value::" + i);

                dynamoParameterRepository.save(dynamoParameter);
            }
        }
    }

    public List<Parameter> retrieveAll() {
        return StreamSupport.stream(dynamoParameterRepository.findAll().spliterator(), false).map(dp -> new Parameter(dp.getName(), dp.getValue())).toList();
    }

    public Optional<Parameter> retrieveByName(String parameterName) {
        List<DynamoParameter> result = dynamoParameterRepository.findByName(parameterName);

        return result.isEmpty() ? Optional.empty() : Optional.of(new Parameter(result.get(0).getName(), result.get(0).getValue()));
    }

    public void save(Parameter parameter) {
        DynamoParameter dynamoParameter = new DynamoParameter();
        dynamoParameter.setId(UUID.randomUUID());
        dynamoParameter.setName(parameter.name());
        dynamoParameter.setValue(parameter.value());

        dynamoParameterRepository.save(dynamoParameter);
    }
}
