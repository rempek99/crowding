package pl.remplewicz.api;

import java.util.List;

import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.model.LoginCredentials;
import pl.remplewicz.model.UserDetails;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICrowdingApi {

    @GET("/api/public/events")
    Call<List<CrowdingEvent>> getEventsCall();


    @POST("/api/events/create")
    Call<CrowdingEvent> createEvent(@Body CrowdingEvent event);

    @POST("/api/public/login")
    Call<LoginCredentials> login(@Body LoginCredentials credentials);

    @POST("/api/public/register")
    Call<LoginCredentials> register(@Query("password") String password,
                                    @Query("username") String username);

    @GET("/api/user/details")
    Call<UserDetails> getDetails();
}
