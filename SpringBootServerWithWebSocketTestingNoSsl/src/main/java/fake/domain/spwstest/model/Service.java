package fake.domain.spwstest.model;

import java.util.Map;

import retrofit.http.GET;

public interface Service
{
    @GET("/htest")
    public Map<String, String> hellowWorld();

}
