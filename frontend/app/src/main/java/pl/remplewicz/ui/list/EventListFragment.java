package pl.remplewicz.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import pl.remplewicz.R;
import pl.remplewicz.model.CrowdingEvent;
import pl.remplewicz.ui.events.EventDetailsFragment;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import pl.remplewicz.util.ResourcesProvider;

public class EventListFragment extends Fragment {

    private EventListViewModel viewModel;

    private ArrayAdapter<CrowdingEvent> adapter;
    private ListView listView;
    private List<CrowdingEvent> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().setTitle(getString(R.string.title_list));
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(EventListViewModel.class);
        viewModel.setResourcesProvider(new ResourcesProvider(getContext()));
        listView = view.findViewById(R.id.crowdingEventList);
        adapter=new ArrayAdapter<CrowdingEvent>(getContext(),
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);

        viewModel.getEvents().observe(getViewLifecycleOwner(),events -> {
            if(events!=null) {
                System.out.println(events.size());
                list.clear();
                list.addAll(events);
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(AuthTokenStore.getInstance().getToken()== null){
                    InformationBar.showInfo(getString(R.string.login_required));
                    return;
                }
                CrowdingEvent itemAtPosition = (CrowdingEvent) parent.getItemAtPosition(position);
                NavigationHelper.goTo(new EventDetailsFragment(itemAtPosition),getString(R.string.event_details_fragment_tag));
            }
        });




        Button fetchButton = view.findViewById(R.id.testButton);
        fetchButton.setOnClickListener(v -> {
            viewModel.fetchEvents();
        });
    }
}