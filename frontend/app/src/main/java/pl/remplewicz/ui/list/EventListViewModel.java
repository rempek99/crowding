package pl.remplewicz.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import lombok.Setter;
import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.ResourcesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListViewModel extends ViewModel {

    @Setter
    private ResourcesProvider resourcesProvider;

    private  MutableLiveData<List<CrowdingEvent>> events;

    public void fetchEvents() {

        RetrofitInstance.getApi().getEventsCall().enqueue(new Callback<List<CrowdingEvent>>() {
            @Override
            public void onResponse(Call<List<CrowdingEvent>> call, Response<List<CrowdingEvent>> response) {
                events.setValue(response.body());
                InformationBar.showInfo(resourcesProvider.getString(R.string.sts_fetched));
            }

            @Override
            public void onFailure(Call<List<CrowdingEvent>> call, Throwable t) {
                InformationBar.showInfo(resourcesProvider.getString(R.string.sts_error));
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
