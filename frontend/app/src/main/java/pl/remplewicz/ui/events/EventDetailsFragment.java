package pl.remplewicz.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import pl.remplewicz.R;

public class EventDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.title_event_details));
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }
}