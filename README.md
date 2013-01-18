# Jersey 2 MultiPart Sample App

Example showing how to implement a multipart resource in a Jersey 2 application.
MultiPart support is implemented as a JAX-RS [Feature](http://jersey.java.net/nonav/apidocs/snapshot/jersey/javax/ws/rs/core/Feature.html), so it should be enabled on the application.

On the server-side:
```java
     final ResourceConfig resourceConfig = new ResourceConfig(MultiPartResource.class);
     resourceConfig.register(MultiPartFeature.class);
```

On the server-side (servlet deployment):
```java
   import org.glassfish.jersey.filter.LoggingFilter;
   import org.glassfish.jersey.media.multipart.MultiPartFeature;

   import javax.ws.rs.core.Application;
   import java.util.HashSet;
   import java.util.Set;

   public class MyApplication extends Application {
       @Override
       public Set<Class<?>> getClasses() {
           final Set<Class<?>> classes = new HashSet<Class<?>>();
           // register resources and features
           classes.add(MultiPartFeature.class);
           classes.add(MultiPartResource.class);
           classes.add(LoggingFilter.class);
           return classes;
       }
   }
```

web.xml:
```
<?xml version="1.0" encoding="UTF-8"?>
    <web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <servlet>
        <servlet-name>Jersey Servlet</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
        <init-param>
            <param-name>javax.ws.rs.Application</param-name>
            <param-value>com.aruld.jersey.multipart.MyApplication</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>Jersey Servlet</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
</web-app>
```

On the client-side:
```java
     final ClientConfig clientConfig = new ClientConfig();
     clientConfig.register(MultiPartFeature.class);
     Client client = ClientFactory.newClient(clientConfig);
```


## Setup

* Clone this repository
* mvn install

## Testing

* Run MultiPartTest from your favourite IDE or run using "mvn test". Here is a sample output:

```
Jan 17, 2013 8:31:31 PM org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory$GrizzlyTestContainer start
INFO: Starting GrizzlyTestContainer...
Jan 17, 2013 8:31:32 PM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [localhost:9998]
Jan 17, 2013 8:31:32 PM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
Jan 17, 2013 8:31:32 PM org.glassfish.jersey.filter.LoggingFilter log
INFO: 1 * LoggingFilter - Request received on thread main
1 > POST http://localhost:9998/multipart
1 > Content-Type: multipart/form-data
--Boundary_1_1939873418_1358490692628
Content-Type: application/xml
Content-Disposition: form-data; filename="input.xml"; modification-date="Fri, 18 Jan 2013 06:31:22 GMT"; size=109; name="xml"

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<person>
    <name>Arul Dhesiaseelan</name>
</person>
--Boundary_1_1939873418_1358490692628--


Processing file # input.xml
Jan 17, 2013 8:31:33 PM org.glassfish.jersey.filter.LoggingFilter log
INFO: 2 * LoggingFilter - Response received on thread main
2 < 200
2 < Date: Fri, 18 Jan 2013 06:31:33 GMT
2 < Transfer-Encoding: chunked
2 < Content-Type: application/xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><person><name>Arul Dhesiaseelan</name><uid>af462941-8580-400c-86a1-0a550c27a24a</uid></person>

Jan 17, 2013 8:31:33 PM org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory$GrizzlyTestContainer stop
INFO: Stopping GrizzlyTestContainer...
Jan 17, 2013 8:31:33 PM org.glassfish.grizzly.http.server.NetworkListener stop
INFO: Stopped listener bound to [localhost:9998]
Jan 17, 2013 8:31:30 PM org.glassfish.jersey.server.ApplicationHandler initialize
INFO: Initiating Jersey application, version Jersey: 2.0-m11 2012-12-21 12:34:15...
```

