package com.aruld.jersey.multipart;

import junit.framework.Assert;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.net.URL;

import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;

/**
 * MultiPart Test (run with Jetty)
 *
 * @author Arul Dhesiaseelan (aruld@acm.org)
 */
public class MultiPartClient {

    public static void main(String[] args) {
        new MultiPartClient().postFormData();
    }

    public void postFormData() {
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(MultiPartFeature.class);
        clientConfig.register(LoggingFilter.class);
        Client client = ClientFactory.newClient(clientConfig);
        WebTarget target = client.target(App.BASE_URI);
        String FILE = "/input.xml";
        URL url = this.getClass().getResource(FILE);
        File data = new File(url.getFile());
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("xml", data, APPLICATION_XML_TYPE);
        MultiPart entity = new FormDataMultiPart().bodyPart(fileDataBodyPart);
        Person person = target.path(App.ROOT_PATH).request().post(Entity.entity(entity, MULTIPART_FORM_DATA_TYPE), Person.class);
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getUid());
        Assert.assertEquals("Arul Dhesiaseelan", person.getName());

    }

}
