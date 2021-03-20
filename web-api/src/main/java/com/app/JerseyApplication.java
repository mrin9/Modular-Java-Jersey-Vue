package com.app;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import com.app.api.HandleInputJsonParseException;
import com.app.util.HibernateUtil;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import com.app.filters.CORSResponseFilter;
import com.app.filters.AuthorizationRequestFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.slf4j.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("/api")
public class JerseyApplication extends ResourceConfig {
    private static final Logger log = LoggerFactory.getLogger(JerseyApplication.class);

    @Context
    UriInfo uri;
    public JerseyApplication(@Context ServletContext servletContext) {
        log.info(String.format("\n\n " +
          "*** Web App Initiated *** " +
          "\n UI      : http://localhost:%s/index.html " +
          "\n API     : http://localhost:%s/api " +
          "\n API Docs: http://localhost:%s/api-docs/index.html \n",
          TomcatStarter.port, TomcatStarter.port, TomcatStarter.port
        ));

        // Setup OpenAPI
        Info info = new Info()
          .title("Mrin Order Processing System")
          .description("This is a sample server Order processing server. using an In-Memory H2 database")
          .contact(new Contact().email("contact-me@example.com"))
          .license(new License()
            .name("Apache 2.0")
            .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        // SecurityScheme api-key
        String securitySchemaName = "api_key";
        SecurityScheme securitySchemeApiKey = new SecurityScheme()
          .type(SecurityScheme.Type.APIKEY)
          .name("Authorization")
          .in(SecurityScheme.In.HEADER);

        OpenApiResource openApiResource = new OpenApiResource();
        // oas.schemaRequirement(securitySchemeApiKey.getName(), securitySchemeApiKey);

        OpenAPI oas = new OpenAPI()
          .info(info)
          .components(new Components().addSecuritySchemes(securitySchemaName, securitySchemeApiKey))
          .addSecurityItem(new SecurityRequirement().addList(securitySchemaName));

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
          .openAPI(oas)
          .prettyPrint(true)
          .resourcePackages(Stream.of("com.app.api").collect(Collectors.toSet()));
        openApiResource.setOpenApiConfiguration(oasConfig);

        // Register Features
        register(MultiPartFeature.class);
        register(JacksonJaxbJsonProvider.class);

        // Register Filters
        register(CORSResponseFilter.class);
        register(AuthorizationRequestFilter.class);
        register(HandleInputJsonParseException.class);

        // Register OpenAPI Resource
        register(openApiResource);

        // Register Source Packages
        packages("com.app.api");

        //Configure Hibernate Session
        HibernateUtil.getSessionFactory();
    }
}
