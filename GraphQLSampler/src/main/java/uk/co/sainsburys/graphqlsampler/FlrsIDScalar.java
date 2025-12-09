package uk.co.sainsburys.graphqlsampler;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;

@DgsScalar(name = "FlrsID")
public class FlrsIDScalar implements Coercing<String, String> {

}
