package uk.co.sainsburys.graphqlsampler;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphQlSamplerApplication {

  public static void main(String[] args) {
    SpringApplication.run(GraphQlSamplerApplication.class, args);
  }

  @DgsComponent
  public class DateScalarRegistration {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
      return builder.scalar(ExtendedScalars.Date);
    }
  }

  @DgsComponent
  public class TimeScalarRegistration {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
      return builder.scalar(ExtendedScalars.Time);
    }
  }
}
