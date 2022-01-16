package pl.remplewicz.util;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import lombok.Setter;
import pl.remplewicz.R;
import pl.remplewicz.ui.user.LoginFragment;
import pl.remplewicz.ui.home.HomeFragment;

public class NavigationHelper {

    @Setter
    public static FragmentActivity mainActivity;

    @Setter
    public static DrawerLayout drawerLayout;

    public static void backToHomeFragment() {
        goTo(new HomeFragment(), mainActivity.getString(R.string.home_fragment_tag));
    }


    public static void showLoginFragment(Fragment fragment) {
        goTo(new LoginFragment(fragment), mainActivity.getString(R.string.login_fragment_tag));
    }

    public static void goTo(Fragment fragment,String tag) {

        //todo check if given fragment is visible
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .setReorderingAllowed(true)
                .addToBackStack(tag)
                .commit();
//        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getTag()).commit();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void previousFragment() {
        mainActivity.getSupportFragmentManager().popBackStack();
    }
}
