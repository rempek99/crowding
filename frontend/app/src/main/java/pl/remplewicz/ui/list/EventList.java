package pl.remplewicz.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.remplewicz.R;
import pl.remplewicz.util.ResourcesProvider;

public class EventList extends Fragment {

    private EventListViewModel viewModel;

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private List<String> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(EventListViewModel.class);
        viewModel.setResourcesProvider(new ResourcesProvider(getContext()));
        listView = view.findViewById(R.id.crowdingEventList);
        adapter=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                list);
        list.add("Tap Fetch to download data from database");
        listView.setAdapter(adapter);

        viewModel.getEvents().observe(getViewLifecycleOwner(),events -> {
            if(events!=null) {
                System.out.println(events.size());
                list.clear();
                list.addAll(events.stream().map(x -> x.getTitle() + "\t" + x.getParticipants() +" / " + x.getSlots()).collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }
        });




        Button fetchButton = view.findViewById(R.id.testButton);
        fetchButton.setOnClickListener(v -> {
            viewModel.fetchEvents();
        });
    }
}