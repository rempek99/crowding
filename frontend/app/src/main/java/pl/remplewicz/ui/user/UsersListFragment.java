package pl.remplewicz.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.List;

import pl.remplewicz.R;
import pl.remplewicz.api.RetrofitInstance;
import pl.remplewicz.model.UserRoles;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.InformationBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    private static final String ACTIVE = "active";
    private static final String FALSE_CHAR = "✘";
    private static final String TRUE_CHAR = "✔";
    private TableLayout usersTable;
    private List<UserRoles> userRolesList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.users_management));
        usersTable = view.findViewById(R.id.users_list_table);
        fetch();
    }

    private void fetch() {
        RetrofitInstance.getApi().getAllUsersWithRoles().enqueue(new Callback<List<UserRoles>>() {
            @Override
            public void onResponse(Call<List<UserRoles>> call, Response<List<UserRoles>> response) {
                if (response.code() == 200) {
                    userRolesList = response.body();
                    updateTable();
                }  else if(response.code() == 403) {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserRoles>> call, Throwable t) {

            }
        });
    }

    private void updateTable() {
        View header = usersTable.findViewById(R.id.users_list_table_header);
        usersTable.removeAllViews();
        usersTable.addView(header);
        for (UserRoles user : userRolesList) {
            TableRow tr = new TableRow(getContext());
            TextView username = new TextView(getContext());
            username.setText(user.getUsername());
            Button activeBtn = new Button(getContext());
            activeBtn.setOnClickListener(new UserPropertyChangeButtonListener(activeBtn, ACTIVE, user.getId()));
            Button userBtn = new Button(getContext());
            userBtn.setOnClickListener(new UserPropertyChangeButtonListener(userBtn, AuthTokenStore.ROLE_USER, user.getId()));
            Button adminBtn = new Button(getContext());
            adminBtn.setOnClickListener(new UserPropertyChangeButtonListener(adminBtn, AuthTokenStore.ROLE_ADMIN, user.getId()));
            if (user.getActive()) {
                activeBtn.setText(TRUE_CHAR);
            } else {
                activeBtn.setText(FALSE_CHAR);
            }
            if (user.getRoles().contains(AuthTokenStore.ROLE_USER)) {
                userBtn.setText(TRUE_CHAR);
            } else {
                userBtn.setText(FALSE_CHAR);
            }
            if (user.getRoles().contains(AuthTokenStore.ROLE_ADMIN)) {
                adminBtn.setText(TRUE_CHAR);
            } else {
                adminBtn.setText(FALSE_CHAR);
            }
            tr.addView(username);
            tr.addView(activeBtn);
            tr.addView(userBtn);
            tr.addView(adminBtn);
            usersTable.addView(tr);
        }
    }

    private void submitChangeRole(Long userid, String role, boolean active) {
        Call<UserRoles> userRolesCall;
        if (active) {
            userRolesCall = RetrofitInstance.getApi().activateUserRole(userid, role);
        } else {
            userRolesCall = RetrofitInstance.getApi().deactivateUserRole(userid, role);
        }
        userRolesCall.enqueue(new Callback<UserRoles>() {
            @Override
            public void onResponse(Call<UserRoles> call, Response<UserRoles> response) {
                if (response.code() == 200) {
                    InformationBar.showInfo(getString(R.string.edited));
                    fetch();
                } else if(response.code() == 403) {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRoles> call, Throwable t) {

            }
        });
    }

    private class UserPropertyChangeButtonListener implements View.OnClickListener {

        Button button;
        String role;
        Long userId;

        public UserPropertyChangeButtonListener(Button button, String role, Long userId) {
            this.button = button;
            this.role = role;
            this.userId = userId;
        }

        @Override
        public void onClick(View v) {
            boolean active = button.getText().equals(FALSE_CHAR);
            if (role.equals(ACTIVE)) {
                submitEnableUser(userId, active);
            } else {
                submitChangeRole(userId, role, active);
            }
        }
    }

    private void submitEnableUser(Long userId, boolean active) {
        RetrofitInstance.getApi().enableUser(userId,active).enqueue(new Callback<UserRoles>() {
            @Override
            public void onResponse(Call<UserRoles> call, Response<UserRoles> response) {
                if (response.code() == 200) {
                    InformationBar.showInfo(getString(R.string.edited));
                    fetch();
                } else if(response.code() == 403) {
                    try {
                        assert response.errorBody() != null;
                        JSONObject json = new JSONObject(response.errorBody().string());
                        InformationBar.showInfo((String) json.get("message"));
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRoles> call, Throwable t) {

            }
        });
    }
}