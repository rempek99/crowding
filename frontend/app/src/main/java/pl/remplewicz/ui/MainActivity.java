package pl.remplewicz.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;

import pl.remplewicz.R;
import pl.remplewicz.ui.user.UserProfileFragment;
import pl.remplewicz.databinding.ActivityMainBinding;
import pl.remplewicz.ui.user.LoginFragment;
import pl.remplewicz.ui.events.CreateEventFragment;
import pl.remplewicz.ui.home.HomeFragment;
import pl.remplewicz.ui.list.EventListFragment;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.CrowdingConstants;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView navbar_username;
    private ImageView navbar_userProfileIcon;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationHelper.setMainActivity(this);
        InformationBar.currentView = getApplicationContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationHelper.setDrawerLayout(drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        navbar_username = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        navbar_userProfileIcon = navigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_icon);
        AuthTokenStore.getInstance().addPropertyChangeListener(evt -> {
            refreshMenu();
        });
        navbar_userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AuthTokenStore.getInstance().getToken() == null) {
                    InformationBar.showInfo(getString(R.string.login_required));
                    return;
                }
                NavigationHelper.goTo(new UserProfileFragment());
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        String apiKey = CrowdingConstants.MAPS_API_KEY;
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        refreshMenu();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void refreshMenu() {
        if(menu==null){return;}
        MenuItem logout = menu.findItem(R.id.navigation_logout);
        MenuItem login = menu.findItem(R.id.navigation_login_fragment);
        if(AuthTokenStore.getInstance().getToken() == null) {
            logout.setVisible(false);
            login.setVisible(true);
        } else {
            logout.setVisible(true);
            login.setVisible(false);
            String username = AuthTokenStore.getInstance().getUsername();
            if (username != null) {
                navbar_username.setText(username);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                NavigationHelper.goTo(new HomeFragment());
                break;
            case R.id.navigation_login_fragment:
                NavigationHelper.goTo(new LoginFragment());
                break;
            case R.id.navigation_create_event_fragment:
                NavigationHelper.goTo(new CreateEventFragment());
                break;
            case R.id.navigation_event_list_fragment:
                NavigationHelper.goTo(new EventListFragment());
                break;
            case R.id.navigation_logout:
                AuthTokenStore.getInstance().invalidateToken();
                break;
        }
        return true;
    }
}