package pl.remplewicz.api;

import java.util.List;

import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.model.CrowdingEventDetails;
import pl.remplewicz.model.LoginCredentials;
import pl.remplewicz.model.UserDetails;
import pl.remplewicz.model.UserRoles;
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

    @GET("api/events/myevents")
    Call<List<CrowdingEvent>> getUserEvents();

    @GET("api/events/myevents/{latitude}/{longitude}")
    Call<List<CrowdingEvent>> getUserNearEvents(@Path("latitude") Double latitude,
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

    @GET("/api/user/details/{username}")
    Call<UserDetails> getUserDetails(@Path("username") String username);

    @PUT("/api/user/details/set")
    Call<UserDetails> setUserDetails(@Body UserDetails userDetails);

    @GET("/api/user/all")
    Call<List<UserRoles>> getAllUsersWithRoles();

    @PUT("/api/user/roles/activate/{id}/{role}")
    Call<UserRoles> activateUserRole(@Path("id") Long userId, @Path("role") String role);

    @PUT("/api/user/roles/deactivate/{id}/{role}")
    Call<UserRoles> deactivateUserRole(@Path("id") Long userId, @Path("role") String role);

    @PUT("api/user/enable/{id}/{active}")
    Call<UserRoles> enableUser(@Path("id") Long userId, @Path("active") Boolean active);

    @GET("api/events/{id}")
    Call<CrowdingEventDetails> getEventDetails(@Path("id") Long id);

    @PUT("api/events/sign/{id}")
    Call<CrowdingEventDetails> signInToEvent(@Path("id") Long id);

    @PUT("api/events/signout/{id}")
    Call<CrowdingEventDetails> signOutFromEvent(@Path("id") Long id);

}
