package com.moviesroad.paulo.moviesroad.presentation.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.moviesroad.paulo.moviesroad.R;
import com.moviesroad.paulo.moviesroad.data.entity.LoginRequest;
import com.moviesroad.paulo.moviesroad.data.entity.UserEntity;
import com.moviesroad.paulo.moviesroad.data.network.api.MovieRoadApi;
import com.moviesroad.paulo.moviesroad.presentation.views.activity.MovieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    MovieRoadApi movieRoadApi;

    @BindView(R.id.text_input_layout_username)
    TextInputLayout usernameTextInputLayout;

    @BindView(R.id.text_input_layout_password)
    TextInputLayout passwordTextInputLayout;

    @BindView(R.id.edit_text_usuario)
    TextInputEditText usernameEditText;

    @BindView(R.id.edit_text_password)
    TextInputEditText passwordEditText;

    @BindView(R.id.button_login)
    Button loginButton;

    @BindView(R.id.linear_layout_loading)
    LinearLayout layoutLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, true);
        ButterKnife.bind(this, view);

        movieRoadApi = MovieRoadApi.getInstance();

        /*usernameEditText = (TextInputEditText)view.findViewById(R.id.edit_text_usuario);
        passwordEditText = (TextInputEditText)view.findViewById(R.id.edit_text_password);
        loginButton = (Button)view.findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "LOGIN BUTTON", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }

    @OnClick(R.id.button_login)
    void onLoginClicked() {

        if (TextUtils.isEmpty(usernameEditText.getText())) {
            usernameTextInputLayout.setErrorEnabled(true);
            usernameTextInputLayout.setError(getString(R.string.invalid_username));
            return;
        }

        if (TextUtils.isEmpty(passwordEditText.getText())) {
            passwordTextInputLayout.setErrorEnabled(true);
            passwordTextInputLayout.setError(getString(R.string.invalid_password));
            return;
        }

        doLogin();
    }

    private void doLogin() {
        //Toast.makeText(getContext(), "LOGIN BUTTON", Toast.LENGTH_SHORT).show();

        layoutLoading.setVisibility(View.VISIBLE);

        movieRoadApi.doLogin(new LoginRequest()
                .withUsername(usernameEditText.getText().toString())
                .withPassword(passwordEditText.getText().toString()))
                .enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if(response.isSuccessful()) {
                    UserEntity userEntity = response.body();
                    movieRoadApi.setSessionToken(userEntity != null ? userEntity.getAccessToken() : "");
                    startActivity(new Intent(getContext(), MovieActivity.class));
                    layoutLoading.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "Usuário / Senha Invalidos", Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                layoutLoading.setVisibility(View.GONE);
            }
        });
    }

    @OnTextChanged(R.id.edit_text_usuario)
    void onEditUsername() {
        usernameTextInputLayout.setErrorEnabled(false);
        usernameTextInputLayout.setError("");
    }

    @OnTextChanged(R.id.edit_text_password)
    void onEditPassword() {
        passwordTextInputLayout.setErrorEnabled(false);
        passwordTextInputLayout.setError("");
    }

}
