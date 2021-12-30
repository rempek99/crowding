package pl.remplewicz.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListViewModel extends ViewModel {

    private  MutableLiveData<List<CrowdingEvent>> events;
    private final MutableLiveData<Integer> status = new MutableLiveData<>();

    public void fetchEvents() {

        RetrofitInstance.getApi().getEventsCall().enqueue(new Callback<List<CrowdingEvent>>() {
            @Override
            public void onResponse(Call<List<CrowdingEvent>> call, Response<List<CrowdingEvent>> response) {
                events.setValue(response.body());
                status.postValue(R.string.sts_fetched);
            }

            @Override
            public void onFailure(Call<List<CrowdingEvent>> call, Throwable t) {
                status.postValue(R.string.sts_error);
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

    public LiveData<Integer> getStatus(){
        return status;
    }
}
