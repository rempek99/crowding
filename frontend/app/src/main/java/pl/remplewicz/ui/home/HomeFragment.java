package pl.remplewicz.ui.home;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.remplewicz.R;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.ui.events.EventDetailsFragment;
import pl.remplewicz.ui.list.EventListViewModel;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import pl.remplewicz.util.PrettyStringFormatter;
import pl.remplewicz.util.ResourcesProvider;

public class HomeFragment extends Fragment {

    private static final float DEFAULT_ZOOM = 10.0f;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private EventListViewModel viewModel;
    private GoogleMap mapGoogle;
    private Map<Marker, CrowdingEvent> markerCrowdingEventMap = new HashMap<>();
    private AlertDialog detailsPopup;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private LatLng defaultLocation = new LatLng(52.76778751720479, 19.558075570858506);
    ;
    private LatLng givenLocation;

    public HomeFragment() {
    }

    public HomeFragment(LatLng givenLocation) {
        this.givenLocation = givenLocation;
    }

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapGoogle = googleMap;
            mapGoogle.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                @Override
                public void onInfoWindowLongClick(@NonNull Marker marker) {
                    createEventDetailDialog(Objects.requireNonNull(markerCrowdingEventMap.get(marker)));

                }
            });
            updateLocationUI();
            getDeviceLocation();
            updateMarkers();
        }
    };


    private void createEventDetailDialog(CrowdingEvent crowdingEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View detailsPopupView = getLayoutInflater().inflate(R.layout.crowding_event_details_popup, null);
        TextView eventTitle = detailsPopupView.findViewById(R.id.popup_event_title);
        eventTitle.setText(crowdingEvent.getTitle());
        TextView eventDate = detailsPopupView.findViewById(R.id.popup_event_date);
        eventDate.setText(PrettyStringFormatter.prettyDate(crowdingEvent.getEventDate()));
        TextView eventSlots = detailsPopupView.findViewById(R.id.popup_event_slots);
        eventSlots.setText(String.format(getString(R.string.numberOfNumberPattern), crowdingEvent.getParticipants(), crowdingEvent.getSlots()));
        builder.setView(detailsPopupView);
        Button detailsBtn = detailsPopupView.findViewById(R.id.popup_event_details_button);
        detailsBtn.setOnClickListener(l -> {
            if (AuthTokenStore.getInstance().getToken() == null) {
                InformationBar.showInfo(getString(R.string.login_required));
                return;
            }
            NavigationHelper.goTo(new EventDetailsFragment(crowdingEvent), getString(R.string.event_details_fragment_tag));
            detailsPopup.dismiss();
        });
        detailsPopup = builder.create();
        detailsPopup.show();
    }

    private List<CrowdingEvent> events = Collections.emptyList();

    private void updateMarkers() {
        if (mapGoogle == null) {
            return;
        }
        mapGoogle.clear();
        List<MarkerOptions> markerOptionsList = new ArrayList<>();


        events.forEach(event -> {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(event.getLatitude(), event.getLongitude()))
                            .title(event.getTitle());
                    Marker marker = mapGoogle.addMarker(markerOptions);
                    markerCrowdingEventMap.put(marker, event);
                }
        );
        if (givenLocation != null) {
            mapGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation, 10));
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.title_home));
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(EventListViewModel.class);
        viewModel.setResourcesProvider(new ResourcesProvider(getContext()));
        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            this.events = events;
            updateMarkers();
        });
        viewModel.fetchEvents();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (mapGoogle == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mapGoogle.setMyLocationEnabled(true);
                mapGoogle.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mapGoogle.setMyLocationEnabled(false);
                mapGoogle.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {


                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                viewModel.setClientLocation(lastKnownLocation);
                                if (givenLocation == null) {
                                    mapGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(lastKnownLocation.getLatitude(),
                                                    lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                }
                                mapGoogle.setMyLocationEnabled(true);
                                mapGoogle.getUiSettings().setMyLocationButtonEnabled(true);
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            if (givenLocation == null) {
                                mapGoogle.moveCamera(CameraUpdateFactory
                                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            }
                            mapGoogle.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}