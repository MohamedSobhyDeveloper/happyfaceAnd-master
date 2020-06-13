package com.hrtzpi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.hrtzpi.R;
import com.hrtzpi.baseactivity.BaseActivity;
import com.hrtzpi.helpers.CallbackRetrofit;
import com.hrtzpi.helpers.Loading;
import com.hrtzpi.helpers.PrefManager;
import com.hrtzpi.helpers.RetrofitModel;
import com.hrtzpi.helpers.StaticMembers;
import com.hrtzpi.models.login_models.ErrorLoginResponse;
import com.hrtzpi.models.login_models.LogInSendModel;
import com.hrtzpi.models.login_models.LoginResponse;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class LogInActivity extends BaseActivity {

    @BindView(R.id.email)
    TextInputEditText emailText;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.password)
    TextInputEditText passwordText;
    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.skip)
    Button skip;
    Loading loading;
    /*
    @BindView(R.id.forgetPassword)
    Button forgetPassword;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        loading=new Loading(this);
        final CardView login = findViewById(R.id.login);
        final CardView register = findViewById(R.id.register);
        register.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), RegistrationActivity.class)));
        login.setOnClickListener(v -> logIn());
//        passwordText.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE)
//                logIn();
//            return false;
//        });
        skip.setOnClickListener(v -> StaticMembers.startActivityOverAll(this, MainActivity.class));
       /* forgetPassword.setOnClickListener((View.OnClickListener) v -> {
            //startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
        });*/
    }

    void logIn() {
        if (StaticMembers.CheckTextInputEditText(emailText, emailLayout, getString(R.string.phone_empty))
                && StaticMembers.CheckTextInputEditText(passwordText, passwordLayout, getString(R.string.password_empty))) {

            if (loading!=null){
                loading.show();

            }
            LogInSendModel sendModel = new LogInSendModel();
//            if (emailText.getText().toString().contains("@"))
//                sendModel.setEmail(emailText.getText().toString());
//            else
            sendModel.setTelephone(emailText.getText().toString());
            sendModel.setPassword(passwordText.getText().toString());
            Call<LoginResponse> call = RetrofitModel.getApi(this).login(sendModel);
            call.enqueue(new CallbackRetrofit<LoginResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                    LoginResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        StaticMembers.toastMessageShortSuccess(getBaseContext(), result.getMessage());
                        PrefManager.getInstance(getBaseContext()).setAPIToken(result.getData().getToken());
                        PrefManager.getInstance(getBaseContext()).setObject(StaticMembers.USER, result.getData().getUser());
                        if (getIntent().getBooleanExtra(StaticMembers.ACTION, false))
                            finish();
                        else
                            StaticMembers.startActivityOverAll(LogInActivity.this, MainActivity.class);
                    } else {
                        try {
                            ErrorLoginResponse errorLoginResponse = null;
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorLoginResponse.class);
                                if (errorLoginResponse != null) {
                                    StaticMembers.toastMessageShortFailed(getBaseContext(), errorLoginResponse.getMessage());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                }
            });
        }
    }

}