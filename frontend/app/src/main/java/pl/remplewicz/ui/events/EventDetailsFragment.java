package pl.remplewicz.ui.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.time.ZonedDateTime;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.model.CrowdingEventDetails;
import pl.remplewicz.ui.home.HomeFragment;
import pl.remplewicz.ui.user.UserProfileFragment;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import pl.remplewicz.util.PrettyStringFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsFragment extends Fragment {

    private CrowdingEvent event;
    private CrowdingEventDetails eventDetails;
    private TextView title, date, organizer, slots, location, description, participantsPercentage;
    private LinearLayout participantsLayout;
    private Button singInButton, showOnMapButton;


    public EventDetailsFragment(CrowdingEvent event) {
        this.event = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.title_event_details));

        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title = view.findViewById(R.id.event_details_title);
        date = view.findViewById(R.id.event_details_date);
        organizer = view.findViewById(R.id.event_details_organizer);
        slots = view.findViewById(R.id.event_details_slots);
        location = view.findViewById(R.id.event_details_location);
        description = view.findViewById(R.id.event_details_description);
        participantsPercentage = view.findViewById(R.id.event_details_participants_percentage);
        participantsLayout = view.findViewById(R.id.event_details_participants_layout);
        singInButton = view.findViewById(R.id.event_details_sign_button);
        showOnMapButton = view.findViewById(R.id.event_details_show_on_map_button);
        if (event.getEventDate().isBefore(ZonedDateTime.now())) {
            singInButton.setEnabled(false);
        }
        singInButton.setOnClickListener(l -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(singInButton.getText())
                    .setMessage(getString(R.string.are_you_sure))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        if (whichButton == DialogInterface.BUTTON_POSITIVE) {
                            if (singInButton.getText().equals(getString(R.string.action_sign_in_short))) {
                                signInToEvent();
                            } else {
                                signOutEvent();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();

        });
        showOnMapButton.setOnClickListener(l -> {
            NavigationHelper.goTo(new HomeFragment(
                            new LatLng(event.getLatitude(), event.getLongitude())),
                    getString(R.string.home_fragment_tag));
        });

        fetchDetails();


        super.onViewCreated(view, savedInstanceState);
    }

    private void signInToEvent() {
        RetrofitInstance.getApi().signInToEvent(event.getId()).enqueue(new Callback<CrowdingEventDetails>() {
            @Override
            public void onResponse(Call<CrowdingEventDetails> call, Response<CrowdingEventDetails> response) {
                if (response.code() == 200) {
                    InformationBar.showInfo(getString(R.string.signed_in));
                    fetchDetails();
                }
                if (response.code() == 409) {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    } catch (Exception ex) {
                        InformationBar.showInfo(getString(R.string.cannotSignIn));
                    }
                }
            }

            @Override
            public void onFailure(Call<CrowdingEventDetails> call, Throwable t) {

            }
        });
    }

    private void signOutEvent() {
        RetrofitInstance.getApi().signOutFromEvent(event.getId()).enqueue(new Callback<CrowdingEventDetails>() {
            @Override
            public void onResponse(Call<CrowdingEventDetails> call, Response<CrowdingEventDetails> response) {
                if (response.code() == 200) {
                    InformationBar.showInfo(getString(R.string.signed_out));
                    fetchDetails();
                }
                if (response.code() == 409) {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    } catch (Exception ex) {
                        InformationBar.showInfo(getString(R.string.cannotSignIn));
                    }
                }
            }

            @Override
            public void onFailure(Call<CrowdingEventDetails> call, Throwable t) {

            }
        });
    }

    private void fetchDetails() {
        RetrofitInstance.getApi().getEventDetails(event.getId()).enqueue(new Callback<CrowdingEventDetails>() {
            @Override
            public void onResponse(Call<CrowdingEventDetails> call, Response<CrowdingEventDetails> response) {
                if (response.code() == 200) {
                    eventDetails = response.body();
                    updateLabels();
                }
            }

            @Override
            public void onFailure(Call<CrowdingEventDetails> call, Throwable t) {

            }
        });
    }

    private void updateLabels() {
        title.setText(eventDetails.getTitle());
        date.setText(PrettyStringFormatter.prettyDate(eventDetails.getEventDate()));
        organizer.setText(eventDetails.getOrganizer().getUsername());
        organizer.setOnClickListener(l -> {
            NavigationHelper.goTo(new UserProfileFragment(eventDetails.getOrganizer()), getString(R.string.user_profile_fragment_tag));
        });
        slots.setText(String.format(getString(R.string.numberOfNumberPattern), eventDetails.getParticipants().size(), eventDetails.getSlots()));
        location.setText(eventDetails.getLocationName() + " " + PrettyStringFormatter.prettyLocation(eventDetails.getLatitude(), eventDetails.getLongitude()));
        description.setText(eventDetails.getDescription());
        double percentage = eventDetails.getParticipants().size();
        percentage = percentage / eventDetails.getSlots() * 100.0;
        if (eventDetails.getEventDate().isBefore(ZonedDateTime.now())) {
            participantsPercentage.setVisibility(View.GONE);
        } else if (percentage >= 75.0) {
            participantsPercentage.setText(String.format(getString(R.string.percentage_reached), String.format("%.0f", percentage)));
        } else {
            participantsPercentage.setText(String.format(getString(R.string.percentage_not_reached), String.format("%.0f", percentage)));
        }
        if (eventDetails.getParticipants().size() > 0) {
            participantsLayout.removeAllViewsInLayout();
        }
        eventDetails.getParticipants().forEach(participant -> {
            TextView user = new TextView(getContext());
            user.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            user.setText(String.format(getString(R.string.ascii_list_elem), participant.getUsername()));
            user.setOnClickListener(l -> {
                NavigationHelper.goTo(new UserProfileFragment(participant), getString(R.string.user_profile_fragment_tag));
            });
            participantsLayout.addView(user);
        });
        if (eventDetails.getParticipants()
                .stream()
                .anyMatch(
                        participant -> participant.getUsername().equals(AuthTokenStore.getInstance().getUsername()))) {
            singInButton.setText(getString(R.string.action_sign_out));
        } else {
            singInButton.setText(getString(R.string.action_sign_in_short));
        }
    }

}