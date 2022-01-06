package pl.remplewicz.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import lombok.Setter;
import pl.remplewicz.R;
import pl.remplewicz.ui.auth.LoginFragment;
import pl.remplewicz.ui.home.HomeFragment;

public class NavigationHelper {

    @Setter
    public static FragmentActivity mainActivity;

    public static void backToHomeFragment() {
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }


    public static void showLoginFragment(Fragment fragment) {
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment(fragment)).commit();
    }

    public static void goTo(Fragment fragment) {
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
