package fake.domain.spwstest.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import retrofit.RestAdapter;
import fake.domain.spwstest.model.Service;

public class ServiceTest
{
//    private static final String SERVICE_BASE_URL = "http://localhost:8080";
    private static final String SERVICE_BASE_URL = "https://dragon:8443";

    private final Service hService;
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SERVICE_BASE_URL).build();
        hService = restAdapter.create(Service.class);
    }

    @Test
    public void testHelloWorld()
        throws Exception
    {
        Map<String, String> result = hService.hellowWorld();
        assertNotNull(result);
        assertEquals("world", result.get("hello"));
    }
}
