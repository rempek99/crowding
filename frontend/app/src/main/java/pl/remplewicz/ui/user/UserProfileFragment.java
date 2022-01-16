package pl.remplewicz.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.Locale;

import lombok.SneakyThrows;
import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.UserDetails;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    private UserDetails userDetails, preFetchUser;
    TextView username, firstname, surname, gender, age;
    Button editButton;

    public UserProfileFragment() {
    }

    public UserProfileFragment(UserDetails preFetchUser) {
        this.preFetchUser = preFetchUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(preFetchUser!=null) {
            requireActivity().setTitle(String.format(getString(R.string.profile_of_pattern),preFetchUser.getUsername()));
        } else {
            requireActivity().setTitle(getString(R.string.title_profile));
        }
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDetails = UserDetails.builder().build();
        username = view.findViewById(R.id.user_profile_username);
        firstname = view.findViewById(R.id.user_profile_firstname);
        surname = view.findViewById(R.id.user_profile_surname);
        gender = view.findViewById(R.id.user_profile_gender);
        age = view.findViewById(R.id.user_profile_age);
        editButton = view.findViewById(R.id.user_profile_edit_button);
        editButton.setOnClickListener(l -> {
            NavigationHelper.goTo(new EditUserProfileFragment(userDetails,this),getString(R.string.edit_profile_fragment_tag));
        });

        if(preFetchUser==null) {
            RetrofitInstance.getApi().getUserDetails().enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        updateData(response.body());
                    } else if (response.code() == 403) {
                        InformationBar.showInfo(getString(R.string.login_required));
                        NavigationHelper.backToHomeFragment();
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {

                }
            });
        } else {
            editButton.setVisibility(View.GONE);
            RetrofitInstance.getApi().getUserDetails(preFetchUser.getUsername()).enqueue(new Callback<UserDetails>() {
                @SneakyThrows
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        updateData(response.body());
                    } else if (response.code() == 403) {
                        InformationBar.showInfo(getString(R.string.login_required));
                        NavigationHelper.backToHomeFragment();
                    } else if (response.code() == 404) {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                        NavigationHelper.previousFragment();
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {

                }
            });
        }
    }

    private void updateData(UserDetails userDetails) {
        username.setText(userDetails.getUsername() == null ? "" : userDetails.getUsername());
        firstname.setText(userDetails.getFirstname() == null ? "" : userDetails.getFirstname());
        surname.setText(userDetails.getSurname() == null ? "" : userDetails.getSurname());
        gender.setText(userDetails.getGender() == null ? "" : userDetails.getGender().name().toLowerCase(Locale.ROOT));
        age.setText(userDetails.getAge() == null ? "" : String.valueOf(userDetails.getAge()));
        this.userDetails = userDetails;
    }
}