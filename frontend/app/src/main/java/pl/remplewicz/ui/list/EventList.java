package pl.remplewicz.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import lombok.var;
import pl.remplewicz.R;
import pl.remplewicz.databinding.FragmentEventListBinding;

public class EventList extends Fragment {

    private FragmentEventListBinding binding;
    private EventListViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = new EventListViewModel(getContext());
        var events = viewModel.getEvents();
        System.out.println(events);
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }
}