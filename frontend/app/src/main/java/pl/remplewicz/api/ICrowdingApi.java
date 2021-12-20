package pl.remplewicz.api;

import java.util.List;

import pl.remplewicz.model.CrowdingEvent;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ICrowdingApi {

    @GET("/api/public/events")
    Call<List<CrowdingEvent>> getEventsCall();

}
