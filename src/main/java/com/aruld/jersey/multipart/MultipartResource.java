package com.aruld.jersey.multipart;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import java.io.InputStream;
import java.util.UUID;

/**
 * MultiPart resource
 *
 * @author Arul Dhesiaseelan (aruld@acm.org)
 */
@Path("multipart")
public class MultiPartResource {

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_XML)
    public Response processForm(@FormDataParam("xml") InputStream is, @FormDataParam("xml") FormDataContentDisposition header) {
        System.out.println("Processing file # " + header.getFileName());
        Person entity = JAXB.unmarshal(is, Person.class);
        entity.setUid(UUID.randomUUID().toString());
        return Response.ok(entity).build();
    }

}
