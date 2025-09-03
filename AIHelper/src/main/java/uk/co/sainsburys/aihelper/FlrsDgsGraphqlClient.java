package uk.co.sainsburys.aihelper;

import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class FlrsDgsGraphqlClient {
    private static final String ENDPOINT = "https://psr-flrs.int.prd.jspaas.uk/graphql";
    private final WebClientGraphQLClient client = new WebClientGraphQLClient(WebClient.create(ENDPOINT));

    public String sendQuery(String query) {
        Mono<GraphQLResponse> response = client.reactiveExecuteQuery(query);
        return response.block().getJson();
    }

    public String getAllSainsburysStores() {
        String query = "query { sainsburysStores { id name } }";
        Mono<GraphQLResponse> response = client.reactiveExecuteQuery(query);
        return response.block().getJson();
    }

    // Example usage
    public static void main(String[] args) {
        FlrsDgsGraphqlClient client = new FlrsDgsGraphqlClient();
        String storesResult = client.getAllSainsburysStores();
        System.out.println("All Sainsburys Stores: " + storesResult);
        String query = "{ __schema { types { name } } }"; // Introspection query
        String result = client.sendQuery(query);
        System.out.println(result);
    }
}
