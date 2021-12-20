package pl.remplewicz.ui.list;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import pl.remplewicz.api.CrowdingApi;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListViewModel extends ViewModel {

    private  MutableLiveData<List<CrowdingEvent>> events;

    public void fetchEvents() {

        RetrofitInstance.getApi().getEventsCall().enqueue(new Callback<List<CrowdingEvent>>() {
            @Override
            public void onResponse(Call<List<CrowdingEvent>> call, Response<List<CrowdingEvent>> response) {
                events.setValue(response.body());
                System.out.println("SFECZOWANE");
            }

            @Override
            public void onFailure(Call<List<CrowdingEvent>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public LiveData<List<CrowdingEvent>> getEvents() {
        if (events == null) {
            events = new MutableLiveData<>();
//            fetchEvents();
        }
        return events;
    }
}
