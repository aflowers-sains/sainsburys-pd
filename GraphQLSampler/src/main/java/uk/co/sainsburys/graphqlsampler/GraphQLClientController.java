package uk.co.sainsburys.graphqlsampler;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.RestClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoresGraphQLQuery;
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoresProjectionRoot;
import uk.co.sainsburys.graphqlsampler.codegen.types.SainsburysStore;

@RestController
public class GraphQLClientController {
  private final GraphQLClient graphQLClient;

  public GraphQLClientController(RestClient.Builder builder) {
    this.graphQLClient = new RestClientGraphQLClient(builder.baseUrl("https://psr-flrs.int.dev.jspaas.uk/graphql").build());
  }

  @GetMapping("/")
  public List<SainsburysStore> retrieveStores() {

    GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(new SainsburysStoresGraphQLQuery.Builder().build(),
        new SainsburysStoresProjectionRoot<>().edges().node().name().code().id());

    String query = graphQLQueryRequest.serialize();
    GraphQLResponse response = graphQLClient.executeQuery(query);

    List<SainsburysStore> stores = response.extractValueAsObject("sainsburysStores.edges[*].node", new TypeRef<List<SainsburysStore>>(){});
    return stores;
  }
}
