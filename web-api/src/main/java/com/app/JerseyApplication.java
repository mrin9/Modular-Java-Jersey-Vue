package com.app;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import com.app.api.HandleInputJsonParseException;
import com.app.util.HibernateUtil;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import com.app.filters.CORSResponseFilter;
import com.app.filters.AuthorizationRequestFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.slf4j.*;

@ApplicationPath("/api/*")
public class JerseyApplication extends ResourceConfig {
    private static Logger log = LoggerFactory.getLogger(JerseyApplication.class);

    public JerseyApplication(@Context ServletContext servletContext) throws Exception {
        log.info(String.format("\n\n *** Jersey Container Initiated *** \n REST APIs available at : localhost:%s/api \n *** *** *** *** *** \n", String.valueOf(TomcatStarter.port)));

        // Register Features
        register(MultiPartFeature.class);
        register(JacksonJaxbJsonProvider.class);
        // Register Filters
        register(CORSResponseFilter.class);
        register(AuthorizationRequestFilter.class);
        register(HandleInputJsonParseException.class);

        // Regster Source Packages
        packages("io.swagger.jaxrs.json");
        packages("io.swagger.jaxrs.listing");
        packages("com.app.api");

        log.info("\n\n *** System Variables ***");
        log.info(" user.home     :" + System.getProperty("user.home"));
        log.info(" user.dir      :" + System.getProperty("user.dir"));
        log.info(" catalina.home :" + System.getProperty("catalina.home"));
        log.info(" catalina.base :" + System.getProperty("catalina.base") +"\n *** *** *** *** *** \n");

        //Configure Hibernate Session
        HibernateUtil.getSessionFactory();

    }


}
