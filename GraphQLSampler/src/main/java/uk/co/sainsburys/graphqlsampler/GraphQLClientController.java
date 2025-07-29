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
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoreGraphQLQuery;
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoreProjectionRoot;
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoresGraphQLQuery;
import uk.co.sainsburys.graphqlsampler.codegen.client.SainsburysStoresProjectionRoot;
import uk.co.sainsburys.graphqlsampler.codegen.types.SainsburysStore;
import uk.co.sainsburys.graphqlsampler.codegen.types.SainsburysStoreFilter;

@RestController
public class GraphQLClientController {
  private final GraphQLClient graphQLClient;

  public GraphQLClientController(RestClient.Builder builder) {
    this.graphQLClient = new RestClientGraphQLClient(builder.baseUrl("https://psr-flrs.int.stg.jspaas.uk/graphql").build());
  }

  @GetMapping("/")
  public List<SainsburysStore> retrieveStores() {

    SainsburysStoreGraphQLQuery storeQuery =
        SainsburysStoreGraphQLQuery.newRequest().filter(SainsburysStoreFilter.newBuilder ().flrsId("U0FJTlNCVVJZU19TVE9SRTowMDhkOGQ4ZS1jZTc2LTRlMzktOThjMS1hMmM0ZGQ1YWM1YWU=").build()).build();
    GraphQLQueryRequest graphQLQueryRequest1 = new GraphQLQueryRequest(storeQuery,
        new SainsburysStoreProjectionRoot<>()
            .name().code().id().placeId()
            .attributes().collectionPickingCapacity());

    System.out.println(graphQLQueryRequest1.serialize());
    System.out.println(graphQLClient.executeQuery(graphQLQueryRequest1.serialize()));

    GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(SainsburysStoresGraphQLQuery.newRequest().build(),
        new SainsburysStoresProjectionRoot<>().edges().node()
            .name().code().id().placeId()
            .attributes().collectionPickingCapacity());

    String query = graphQLQueryRequest.serialize();
    GraphQLResponse response = graphQLClient.executeQuery(query);

    List<SainsburysStore> stores = response.extractValueAsObject("sainsburysStores.edges[*].node", new TypeRef<List<SainsburysStore>>(){});
    return stores;
  }
}
