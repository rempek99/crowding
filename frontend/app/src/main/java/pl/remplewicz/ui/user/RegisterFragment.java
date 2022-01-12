package pl.remplewicz.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.LoginCredentials;
import pl.remplewicz.util.InformationBar;
import pl.remplewicz.util.NavigationHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText loginInput, passwordInput, passwordConfirmInput;
    private Button registerBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.title_register));
        this.view = view;
        loginInput = view.findViewById(R.id.registerEditLogin);
        passwordInput = view.findViewById(R.id.registerEditPassword);
        passwordConfirmInput = view.findViewById(R.id.registerEditPasswordConfirm);
        registerBtn = view.findViewById(R.id.registerRegisterBtn);

        registerBtn.setOnClickListener(v -> submitRegister());


    }

    private void submitRegister() {
        if (!validate()) {
            return;
        }
        final String login, password;
        login = loginInput.getText().toString();
        password = passwordInput.getText().toString();
        RetrofitInstance.getApi().register(password,login).enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                if(response.code() == 200) {
                    InformationBar.showInfo(getResources().getString(R.string.registered_successed));
                    NavigationHelper.goTo(new LoginFragment());
                }
                if(response.code() == 409){
                    InformationBar.showInfo(getResources().getString(R.string.login_taken));
                    loginInput.setError(getString(R.string.login_taken));
                }
            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                InformationBar.showInfo(getString(R.string.sts_error));
            }
        });
    }

    private boolean validate() {
        final String login, password, passwordConfirm;
        password = passwordInput.getText().toString();
        passwordConfirm = passwordConfirmInput.getText().toString();
        if (!password.equals(passwordConfirm)) {
            passwordConfirmInput.setError(getString(R.string.different_passwords));
            InformationBar.showInfo(getString(R.string.different_passwords));
            return false;
        }
        return true;
    }
}