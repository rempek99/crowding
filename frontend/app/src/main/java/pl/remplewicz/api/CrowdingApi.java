package pl.remplewicz.api;

import java.io.IOException;
import java.util.List;

import pl.remplewicz.model.CrowdingEvent;
import retrofit2.Call;
import retrofit2.Response;

public class CrowdingApi {

    private static ICrowdingApi api;
    private static CrowdingApi instance;

    private CrowdingApi(){

    }

    public static CrowdingApi getInstance() {
        if(instance == null){
            api = RetrofitInstance.getApi();
            instance = new CrowdingApi();
        }
        return instance;
    }

    private Object executeCall(Call call){
        try {
            Response response = call.execute();
            return response.body();
        } catch (IOException ex){
            return null;
        }
    }

    // Methods

    public List<CrowdingEvent> getEvents(){
        return (List<CrowdingEvent>) executeCall(api.getEventsCall());
    }

}
