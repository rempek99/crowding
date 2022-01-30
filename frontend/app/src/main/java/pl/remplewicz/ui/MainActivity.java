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
import com.google.android.material.navigation.NavigationView;

import pl.remplewicz.R;
import pl.remplewicz.databinding.ActivityMainBinding;
import pl.remplewicz.ui.events.CreateEventFragment;
import pl.remplewicz.ui.list.EventListFragment;
import pl.remplewicz.ui.list.MyEventsFragment;
import pl.remplewicz.ui.user.LoginFragment;
import pl.remplewicz.ui.user.UserProfileFragment;
import pl.remplewicz.ui.user.UsersListFragment;
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
        InformationBar.currentView = getBaseContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationHelper.setDrawerLayout(drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navbar_username = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        menu = navigationView.getMenu();
        navbar_userProfileIcon = navigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_icon);
        AuthTokenStore.getInstance().addPropertyChangeListener(evt -> {
            refreshMenu();
        });
        navbar_userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AuthTokenStore.getInstance().getToken() == null) {
                    InformationBar.showInfo(getString(R.string.login_required));
                    return;
                }
                NavigationHelper.goTo(new UserProfileFragment(),getString(R.string.user_profile_fragment_tag));
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
        if (menu == null) {
            return;
        }
        MenuItem logout = menu.findItem(R.id.navigation_logout);
        MenuItem login = menu.findItem(R.id.navigation_login_fragment);
        MenuItem usersList = menu.findItem(R.id.navigation_users_list);
        MenuItem myEvents = menu.findItem(R.id.navigation_my_events_framgent);
        MenuItem addEvent = menu.findItem(R.id.navigation_create_event_fragment);
        usersList.setVisible(false);
        if (AuthTokenStore.getInstance().getToken() == null) {
            logout.setEnabled(false);
            // todo Login button is not visible
            login.setEnabled(true);
            myEvents.setEnabled(false);
            addEvent.setEnabled(false);
            navbar_username.setText(R.string.not_logged_in);
        } else {
            logout.setEnabled(true);
            login.setEnabled(false);
            myEvents.setEnabled(true);
            addEvent.setEnabled(true);
            String username = AuthTokenStore.getInstance().getUsername();
            if (username != null) {
                if(AuthTokenStore.getInstance().isAdmin()){
                    navbar_username.setText(username + " (ADMIN)");
                    usersList.setVisible(true);
                } else {
                    navbar_username.setText(username);
                }
            }
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean userLoggedIn = AuthTokenStore.getInstance().getToken() == null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                NavigationHelper.backToHomeFragment();
                break;
            case R.id.navigation_login_fragment:
                NavigationHelper.goTo(new LoginFragment(),getString(R.string.login_fragment_tag));
                break;
            case R.id.navigation_create_event_fragment:
                if (userLoggedIn) {
                    InformationBar.showInfo(getString(R.string.login_required));
                    return true;
                }
                NavigationHelper.goTo(new CreateEventFragment(),getString(R.string.create_event_fragment_tag));
                break;
            case R.id.navigation_event_list_fragment:
                NavigationHelper.goTo(new EventListFragment(),getString(R.string.event_list_fragment_tag));
                break;
            case R.id.navigation_logout:
                AuthTokenStore.getInstance().invalidateToken();
                InformationBar.showInfo(getString(R.string.logged_out));
                NavigationHelper.backToHomeFragment();
                break;
            case R.id.navigation_my_events_framgent:
                if (userLoggedIn) {
                    InformationBar.showInfo(getString(R.string.login_required));
                    return true;
                }
                NavigationHelper.goTo(new MyEventsFragment(),getString(R.string.my_events_fragment_tag));
                break;
            case R.id.navigation_users_list:
                if (userLoggedIn) {
                    InformationBar.showInfo(getString(R.string.login_required));
                    return true;
                }
                if(!AuthTokenStore.getInstance().isAdmin()){
                    InformationBar.showInfo(getString(R.string.forbidden));
                    return true;
                }
                NavigationHelper.goTo(new UsersListFragment(),getString(R.string.users_list_fragment_tag));
        }
        return true;
    }

}