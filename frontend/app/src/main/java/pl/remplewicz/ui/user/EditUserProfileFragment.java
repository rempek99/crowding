package pl.remplewicz.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.UserDetails;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileFragment extends Fragment {

    private UserDetails userDetails;
    private Fragment parentFragment;

    TextView username;
    EditText firstname, surname, age;
    RadioGroup gender;
    RadioButton maleGender, femaleGender;
    Button saveButton;

    public EditUserProfileFragment(UserDetails userDetails,Fragment parentFragment) {
        this.userDetails = userDetails;
        this.parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.edit_user_profile_username);
        firstname = view.findViewById(R.id.edit_user_profile_firstname);
        surname = view.findViewById(R.id.edit_user_profile_surname);
        gender = view.findViewById(R.id.edit_user_profile_gender);
        maleGender = view.findViewById(R.id.edit_user_profile_gender_male);
        femaleGender = view.findViewById(R.id.edit_user_profile_gender_female);
        age = view.findViewById(R.id.edit_user_profile_age);
        saveButton = view.findViewById(R.id.edit_user_profile_edit_button);
        username.setText(userDetails.getUsername());
        firstname.setText(userDetails.getFirstname());
        surname.setText(userDetails.getSurname());
        if (userDetails.getGender() != null && userDetails.getGender().equals(UserDetails.Gender.FEMALE)) {
            femaleGender.setChecked(true);
        } else if (userDetails.getGender() != null && userDetails.getGender().equals(UserDetails.Gender.MALE)) {
            maleGender.setChecked(true);
        }
        if (userDetails.getAge() != null) {
            age.setText(String.valueOf(userDetails.getAge()));
        } else {
            age.setText("");
        }
        saveButton.setOnClickListener(l -> {
            UserDetails.Gender genderValue = null;
            if(femaleGender.isChecked()){
                genderValue = UserDetails.Gender.FEMALE;
            }
            if(maleGender.isChecked()){
                genderValue = UserDetails.Gender.MALE;
            }
            userDetails = UserDetails.builder()
                    .username(username.getText().toString())
                    .firstname(firstname.getText().toString())
                    .surname(surname.getText().toString())
                    .age(Integer.parseInt(age.getText().toString()))
                    .gender(genderValue)
                    .build();
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.new_user_details))
                    .setMessage(getString(R.string.are_you_sure))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        if(whichButton == DialogInterface.BUTTON_POSITIVE){
                            submitNewDetails();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });
    }

    void submitNewDetails(){
        RetrofitInstance.getApi().setUserDetails(userDetails).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.code() == 200) {
                    InformationBar.showInfo(getString(R.string.eddited));
                    NavigationHelper.goTo(parentFragment);
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    } catch (Exception ex) {
                        InformationBar.showInfo(getString(R.string.sts_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                InformationBar.showInfo(getString(R.string.sts_error));
            }
        });
    }
}