package pl.remplewicz.ui.list;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.remplewicz.model.CrowdingEvent;

public class EventListViewModel extends ViewModel {

    private final Context context;
    private final MutableLiveData<List<CrowdingEvent>> events = new MutableLiveData<>();

    public EventListViewModel(Context context) {
        this.context = context;
        fetchEvents();
    }

    private void fetchEvents() {
        List<CrowdingEvent> events = new ArrayList<CrowdingEvent>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        System.out.println("eeeeeeeeeeeeelo");
        String url = "http://192.168.1.10:8080/api/public/events";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Gson gson = new Gson();
                    System.out.println("-----------------------------");
                    System.out.println(response.toString());
                    CrowdingEvent[] responseEvents = gson.fromJson(response.toString(), CrowdingEvent[].class);
                    this.events.setValue(Arrays.asList(responseEvents));
                },
                error -> {
                    System.out.println(error.getMessage());

                });
//        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
//                (Request.Method.GET, url, null, response -> {
//                    Gson gson = new Gson();
//                    System.out.println("-----------------------------");
//                    System.out.println(response.toString());
//                    CrowdingEvent[] responseEvents = gson.fromJson(response.toString(), CrowdingEvent[].class);
//                    this.events.setValue(Arrays.asList(responseEvents));
//                }, error -> {
//                    System.out.println(error.getMessage());
//
//                });
        requestQueue.add(jsonArrayRequest);
        this.events.setValue(events);
    }

    public List<CrowdingEvent> getEvents() {
        if (events.getValue() == null) {
            fetchEvents();
        }
        return events.getValue();
    }
}
