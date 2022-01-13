package pl.remplewicz.api;

import java.util.List;

import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.model.CrowdingEventDetails;
import pl.remplewicz.model.LoginCredentials;
import pl.remplewicz.model.UserDetails;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICrowdingApi {

    @GET("/api/public/events")
    Call<List<CrowdingEvent>> getEventsCall();

    @GET("/api/public/events/nearest/{latitude}/{longitude}")
    Call<List<CrowdingEvent>> getNearestEventsCall(@Path("latitude") Double latitude,
                                                   @Path("longitude") Double longitude);


    @POST("/api/events/create")
    Call<CrowdingEvent> createEvent(@Body CrowdingEvent event);

    @POST("/api/public/login")
    Call<LoginCredentials> login(@Body LoginCredentials credentials);

    @POST("/api/public/register")
    Call<LoginCredentials> register(@Query("password") String password,
                                    @Query("username") String username);

    @GET("/api/user/details")
    Call<UserDetails> getUserDetails();

    @PUT("/api/user/details/set")
    Call<UserDetails> setUserDetails(@Body UserDetails userDetails);

    @GET("api/events/{id}")
    Call<CrowdingEventDetails> getEventDetails(@Path("id") Long id);

    @PUT("api/events/sign/{id}")
    Call<CrowdingEventDetails> signInToEvent(@Path("id") Long id);

    @PUT("api/events/signout/{id}")
    Call<CrowdingEventDetails> signOutFromEvent(@Path("id") Long id);

}
