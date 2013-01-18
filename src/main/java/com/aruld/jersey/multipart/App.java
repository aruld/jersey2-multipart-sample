package com.aruld.jersey.multipart;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Arul Dhesiaseelan (aruld@acm.org)
 */
public class App {
    private static final URI BASE_URI = URI.create("http://localhost:8080/");
    public static final String ROOT_PATH = "multipart";
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            System.out.println("\"MultiPart\" Jersey Example App");
            final ResourceConfig resourceConfig = new ResourceConfig(MultiPartResource.class);
            resourceConfig.registerInstances(new LoggingFilter(LOGGER, true));
            resourceConfig.register(MultiPartFeature.class);
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
            System.out.println(String.format("Application started.\nTry out %s%s\nHit enter to stop it...", BASE_URI, ROOT_PATH));
            System.in.read();
            server.stop();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}