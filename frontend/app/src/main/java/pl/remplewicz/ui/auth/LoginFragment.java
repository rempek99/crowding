package pl.remplewicz.ui.auth;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.LoginCredentials;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    EditText loginInput, passwordInput;
    private Button regitsterNavBtn, loginButton;
    private String login, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginInput = view.findViewById(R.id.loginEditLogin);
        passwordInput = view.findViewById(R.id.loginEditPassword);

        passwordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submitLogin();
                    handled = true;
                }
                return handled;
            }
        });

        regitsterNavBtn = view.findViewById(R.id.loginRegisterBtn);
        regitsterNavBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));

        loginButton = view.findViewById(R.id.loginLoginBtn);
        loginButton.setOnClickListener(v ->
        {
            submitLogin();
        });

    }

    private void submitLogin() {
        login = loginInput.getText().toString();
        password = passwordInput.getText().toString();
        RetrofitInstance.getApi().login(new LoginCredentials(login, password)).enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                if(response.code() == 200) {
                    String token = response.headers().get("Authorization");
                    AuthTokenStore.getInstance().setToken(token);
                    InformationBar.showInfo(getResources().getString(R.string.login_successed));
                    requireActivity().finish();
                }
                if(response.code() == 401){
                    InformationBar.showInfo(getResources().getString(R.string.login_failed));
                }
            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}