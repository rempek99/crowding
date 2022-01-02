package pl.remplewicz.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.remplewicz.R;
import pl.remplewicz.ui.auth.AuthActivity;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;


public class MenuFragment extends Fragment {

    private Button loginActivityBtn;
    private Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginActivityBtn = view.findViewById(R.id.menu_login_activity);
        loginActivityBtn.setOnClickListener(v -> {
            Intent goToMenu = new Intent(getContext(), AuthActivity.class);
            startActivity(goToMenu);
        });

        logoutBtn = view.findViewById(R.id.menu_logout);
        logoutBtn.setOnClickListener(v -> {
            AuthTokenStore.getInstance().invalidateToken();
            InformationBar.showInfo(getResources().getString(R.string.logged_out));
        });
    }
}