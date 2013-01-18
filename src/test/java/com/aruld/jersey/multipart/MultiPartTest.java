package com.aruld.jersey.multipart;

import junit.framework.Assert;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import java.io.File;
import java.net.URL;

import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;

/**
 * MultiPart Test
 *
 * @author Arul Dhesiaseelan (aruld@acm.org)
 */
public class MultiPartTest extends JerseyTest {

    @Override
    protected ResourceConfig configure() {
        // mvn test -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory"
        // mvn test -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory"
        // mvn test -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.jdkhttp.JdkHttpServerTestContainerFactory"
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        final ResourceConfig resourceConfig = new ResourceConfig(MultiPartResource.class);
        resourceConfig.register(MultiPartFeature.class);
        return resourceConfig;
    }

    @Override
    protected void configureClient(ClientConfig clientConfig) {
        clientConfig.register(MultiPartFeature.class);
    }

    @Test
    public void postFormData() {
        String FILE = "/input.xml";
        URL url = this.getClass().getResource(FILE);
        File data = new File(url.getFile());
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("xml", data, APPLICATION_XML_TYPE);
        MultiPart entity = new FormDataMultiPart().bodyPart(fileDataBodyPart);
        Person person = target().path(App.ROOT_PATH).request().post(Entity.entity(entity, MULTIPART_FORM_DATA_TYPE), Person.class);
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getUid());
        Assert.assertEquals("Arul Dhesiaseelan", person.getName());

    }

}
