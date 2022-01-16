package pl.remplewicz.ui.list;

import android.location.Location;

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

    private MutableLiveData<List<CrowdingEvent>> events;

    private MutableLiveData<List<CrowdingEvent>> userEvents;

    private Location clientLocation;

    public void fetchEvents() {
        Call<List<CrowdingEvent>> call;
        if (clientLocation != null) {
            call = RetrofitInstance.getApi().getNearestEventsCall(clientLocation.getLatitude(), clientLocation.getLongitude());
        } else {
            call = RetrofitInstance.getApi().getEventsCall();
        }
        call.enqueue(new Callback<List<CrowdingEvent>>() {
            @Override
            public void onResponse(Call<List<CrowdingEvent>> call, Response<List<CrowdingEvent>> response) {
                events.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CrowdingEvent>> call, Throwable t) {
                InformationBar.showInfo(resourcesProvider.getString(R.string.sts_error));
            }
        });
    }

    public void setClientLocation(Location clientLocation) {
        this.clientLocation = clientLocation;
        fetchEvents();
    }

    public LiveData<List<CrowdingEvent>> getEvents() {
        if (events == null) {
            events = new MutableLiveData<>();
            fetchEvents();
        }
        return events;
    }

    public LiveData<List<CrowdingEvent>> getUserEvents() {
        if (userEvents == null) {
            userEvents = new MutableLiveData<>();
            fetchEvents();
        }
        return userEvents;
    }

    public void fetchUserEvents() {
        Call<List<CrowdingEvent>> call;
        if (clientLocation != null) {
            call = RetrofitInstance.getApi().getUserNearEvents(clientLocation.getLatitude(), clientLocation.getLongitude());
        } else {
            call = RetrofitInstance.getApi().getUserEvents();
        }
        call.enqueue(new Callback<List<CrowdingEvent>>() {
            @Override
            public void onResponse(Call<List<CrowdingEvent>> call, Response<List<CrowdingEvent>> response) {
                userEvents.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CrowdingEvent>> call, Throwable t) {
                InformationBar.showInfo(resourcesProvider.getString(R.string.sts_error));
            }
        });
    }
}
