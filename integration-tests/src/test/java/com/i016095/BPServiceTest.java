package com.i016095;
//import com.jayway.restassured.RestAssured;       // adjust when using older version
import io.restassured.RestAssured;
//import com.jayway.restassured.http.ContentType;   // adjust when using older version
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.net.URL;
import java.net.URISyntaxException;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.testutil.MockUtil;

//import static com.jayway.restassured.RestAssured.given;  // adjust when using older version
import static io.restassured.RestAssured.given;             


@RunWith( Arquillian.class )
public class BPServiceTest
{
    private static final MockUtil mockUtil = new MockUtil();
    private static final Logger logger = CloudLoggerFactory.getLogger(BPServiceTest.class);

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(BPServlet.class);
    }

    @BeforeClass
    public static void beforeClass() throws URISyntaxException
    {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Before
    public void before()
    {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testService()
    {
        // JSON schema validation from resource definition
        final JsonSchemaValidator jsonValidator = JsonSchemaValidator.matchesJsonSchemaInClasspath("businesspartners-schema.json");

        // HTTP GET response OK, JSON header and valid schema
        given().
                get("/businesspartners").
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body(jsonValidator);
    }
}
