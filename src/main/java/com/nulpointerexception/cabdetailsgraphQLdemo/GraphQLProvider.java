package com.nulpointerexception.cabdetailsgraphQLdemo;

import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Component
public class GraphQLProvider {
    public static final String SCHEMA_DEFINITION = "schema.graphqls";
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL(){
        return this.graphQL;
    }

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource(SCHEMA_DEFINITION);
        String sdl = Resources.toString(url, Charset.forName("UTF-8"));
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("cabById", graphQLDataFetchers.getCabByIdDateFetcher()))
                .type(TypeRuntimeWiring.newTypeWiring("Cab")
                        .dataFetcher("driver", graphQLDataFetchers.getDriverDataFetcher()))
                .build();
    }
}
