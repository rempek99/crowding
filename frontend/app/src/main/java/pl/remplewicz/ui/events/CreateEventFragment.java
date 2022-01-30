package pl.remplewicz.ui.events;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.Arrays;

import lombok.SneakyThrows;
import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import pl.remplewicz.util.PrettyStringFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventFragment extends Fragment {

    private EditText title, description, slots;
    private Button createButton, datePickerButton;
    private ZonedDateTime eventDateTime;
    private boolean datetimePickerOpened = false;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private double latitude,longitude;
    private String locationName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.title_create_event));

        title = view.findViewById(R.id.createEditTitle);
        datePickerButton = view.findViewById(R.id.createEditDate);
        description = view.findViewById(R.id.createEditDescription);
        slots = view.findViewById(R.id.createEditSlots);
        createButton = view.findViewById(R.id.createEventBtn);
        createButton.setOnClickListener(v -> submit());
        datePickerButton.setOnClickListener(v -> showDateTimePicker());


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountries("PL");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                locationName = place.getName();
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    private void showDateTimePicker() {
        if (!datetimePickerOpened) {
            datetimePickerOpened = true;
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            // Get Current Time
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mHour = hourOfDay;
                            mMinute = minute;
                            eventDateTime = ZonedDateTime.of(mYear, mMonth, mDay, mHour, mMinute, 0, 0, ZonedDateTime.now().getZone());
                            datePickerButton.setText(PrettyStringFormatter.prettyDate(eventDateTime));
                            datetimePickerOpened = false;
                        }
                    }, mHour, mMinute, true);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear + 1;
                            mDay = dayOfMonth;
                            timePickerDialog.show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void submit() {
        System.out.println(datePickerButton.getText().toString());
        CrowdingEvent event = CrowdingEvent.builder()
                .title(title.getText().toString())
                .eventDate(eventDateTime)
                .description(description.getText().toString())
                .latitude(latitude)
                .longitude(longitude)
                .locationName(locationName)
                .slots(Integer.parseInt(slots.getText().toString()))
                .build();

        try {
            RetrofitInstance.getApi().createEvent(event).enqueue(new Callback<CrowdingEvent>() {
                @SneakyThrows
                @Override
                public void onResponse(Call<CrowdingEvent> call, Response<CrowdingEvent> response) {
                    if (response.code() == 200) {
                        InformationBar.showInfo(getString(R.string.event_created));
                        NavigationHelper.backToHomeFragment();
                    }
                    if (response.code() == 401) {
                        InformationBar.showInfo(getString(R.string.login_required));
                        NavigationHelper.showLoginFragment(CreateEventFragment.this);
                    }
                    if (response.code() == 409) {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    }
                }

                @Override
                public void onFailure(Call<CrowdingEvent> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}