package pl.remplewicz.ui.create;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.ArrayMap;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import okhttp3.RequestBody;
import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.model.response.CrowdingEventResponse;
import pl.remplewicz.util.CustomObjectMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventFragment extends Fragment {

    private EditText title, date, longitude, latitude, description, slots;
    private Button createButton;
    private ZonedDateTime eventDateTime;
    private boolean datetimePickerOpened = false;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.createEditTitle);
        date = view.findViewById(R.id.createEditDate);
        longitude = view.findViewById(R.id.createEditLongitude);
        latitude = view.findViewById(R.id.createEditLatitude);
        description = view.findViewById(R.id.createEditDescription);
        slots = view.findViewById(R.id.createEditSlots);
        createButton = view.findViewById(R.id.createEventBtn);
        createButton.setOnClickListener(v -> submit());
        date.setOnClickListener(v -> showDateTimePicker());


    }

    private void showDateTimePicker() {
        if (!datetimePickerOpened) {
            datetimePickerOpened = true;
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            final StringBuilder dateTimeTextBuilder = new StringBuilder();
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
                            dateTimeTextBuilder.append(hourOfDay + ":" + minute);
                            mHour = hourOfDay;
                            mMinute = minute;
                            date.setText(dateTimeTextBuilder.toString());
                            eventDateTime = ZonedDateTime.of(mYear, mMonth, mDay, mHour, mMinute, 0, 0, ZonedDateTime.now().getZone());
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
                            dateTimeTextBuilder.append(mDay + "-" + mMonth + "-" + mYear + " ");
                            timePickerDialog.show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void submit() {
        System.out.println(date.getText().toString());
        CrowdingEvent event = CrowdingEvent.builder()
                .title(title.getText().toString())
                .eventDate(eventDateTime)
                .description(description.getText().toString())
                .latitude(Double.parseDouble(latitude.getText().toString()))
                .longitude(Double.parseDouble(longitude.getText().toString()))
                .slots(Integer.parseInt(slots.getText().toString()))
                .build();


//        Map<String, Object> jsonParams = new ArrayMap<>();
////put something inside the map, could be null
//        jsonParams.put("title","testTitle");
//        jsonParams.put("eventDate","2021-12-12T12:00:00+01:00");
//        jsonParams.put("slots",5);
//        jsonParams.put("latitude",51.2);
//        jsonParams.put("longitude",52.3);
        try {
//            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
            RetrofitInstance.getApi().createEvent(event).enqueue(new Callback<CrowdingEvent>() {
                @Override
                public void onResponse(Call<CrowdingEvent> call, Response<CrowdingEvent> response) {
                    System.out.println(response);
                }

                @Override
                public void onFailure(Call<CrowdingEvent> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}