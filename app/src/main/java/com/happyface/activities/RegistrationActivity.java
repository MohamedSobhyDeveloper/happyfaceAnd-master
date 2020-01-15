package com.happyface.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.happyface.R;
import com.happyface.baseactivity.BaseActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.area_models.AreaResponse;
import com.happyface.models.area_models.DataItem;
import com.happyface.models.registration_models.ErrorRegistrationResponse;
import com.happyface.models.registration_models.RegistrationResponse;
import com.happyface.models.registration_models.RegistrationSendModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.email)
    TextInputEditText emailText;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.name)
    TextInputEditText nameText;
    @BindView(R.id.nameLayout)
    TextInputLayout nameLayout;
    @BindView(R.id.mobile)
    TextInputEditText phone;
    @BindView(R.id.phoneLayout)
    TextInputLayout phoneLayout;
    @BindView(R.id.govLayout)
    TextInputLayout govLayout;
    @BindView(R.id.gov)
    TextInputEditText gov;
    @BindView(R.id.area)
    AppCompatSpinner area;
    @BindView(R.id.blockLayout)
    TextInputLayout blockLayout;
    @BindView(R.id.block)
    TextInputEditText block;
    @BindView(R.id.streetLayout)
    TextInputLayout streetLayout;
    @BindView(R.id.street)
    TextInputEditText street;
    @BindView(R.id.avenueLayout)
    TextInputLayout avenueLayout;
    @BindView(R.id.avenue)
    TextInputEditText avenue;
    @BindView(R.id.remarkAddressLayout)
    TextInputLayout remarkAddressLayout;
    @BindView(R.id.remarkAddress)
    TextInputEditText remarkAddress;
    @BindView(R.id.houseNoLayout)
    TextInputLayout houseNoLayout;
    @BindView(R.id.houseNo)
    TextInputEditText houseNo;
    @BindView(R.id.genderRadio)
    RadioGroup genderRadio;
    @BindView(R.id.birthdayButton)
    Button birthdayButton;
    @BindView(R.id.password)
    TextInputEditText passwordText;
    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.confirmPassword)
    TextInputEditText confirmPassword;
    @BindView(R.id.confirmPasswordLayout)
    TextInputLayout confirmPasswordLayout;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.skip)
    Button skip;
    @BindView(R.id.currentLocation)
    Button currentLocation;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        findViewById(R.id.register).setOnClickListener(v -> register());
        confirmPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
                register();
            return false;
        });
        calendar = Calendar.getInstance();
        birthdayButton.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) ->
            {
                calendar.set(year, month, dayOfMonth);
                birthdayButton.setText(String.format(Locale.getDefault(), getString(R.string.birthday_s), StaticMembers.getDate(calendar)));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePicker.show();
        });
        skip.setOnClickListener(v -> StaticMembers.startActivityOverAll(this, MainActivity.class));
        currentLocation.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(StaticMembers.LAT, slat);
            intent.putExtra(StaticMembers.LONG, slong);
            startActivityForResult(intent, StaticMembers.LOCATION_CODE);
        });
        List<String> areas = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areas);
        area.setAdapter(adapter);
        getAreas(areas, adapter);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = areas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAreas(List<String> list, ArrayAdapter<String> adapter) {
        AreaResponse areaResponse = (AreaResponse) PrefManager.getInstance(this).getObject(StaticMembers.AREA, AreaResponse.class);
        if (areaResponse != null) {
            list.clear();
            for (DataItem dataItem : areaResponse.getData()) {
                if (dataItem.getName() != null)
                    list.add(dataItem.getName());
            }
            adapter.notifyDataSetChanged();
        }
        Call<AreaResponse> call = RetrofitModel.getApi(this).getAreas();
        call.enqueue(new CallbackRetrofit<AreaResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<AreaResponse> call, @NotNull Response<AreaResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    list.clear();
                    for (DataItem dataItem : response.body().getData()) {
                        if (dataItem.getName() != null)
                            list.add(dataItem.getName());
                    }
                    PrefManager.getInstance(getApplicationContext()).setObject(StaticMembers.AREA, response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AreaResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }

    double slat, slong;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == StaticMembers.LOCATION_CODE && data != null) {
                slat = data.getDoubleExtra(StaticMembers.LAT, 0);
                slong = data.getDoubleExtra(StaticMembers.LONG, 0);
                currentLocation.setText(String.format(Locale.getDefault(), getString(R.string.current_location_s), slat, slong));
            }
        }
    }

    String selectedArea;

    void register() {
        if (StaticMembers.CheckTextInputEditText(phone, phoneLayout, getString(R.string.phone_empty)) &&
                StaticMembers.CheckTextInputEditText(passwordText, passwordLayout, getString(R.string.phone_empty)) &&
                //StaticMembers.CheckValidationPassword(passwordText, passwordLayout, getString(R.string.password_empty), getString(R.string.password_invalid_error)) &&
                StaticMembers.CheckTextInputEditText(confirmPassword, confirmPasswordLayout, getString(R.string.confirm_password_empty))) {
            final String password = passwordText.getText().toString();
            final String confirm = confirmPassword.getText().toString();
            if (!password.equals(confirm)) {
                StaticMembers.toastMessageShortFailed(getBaseContext(), this.getString(R.string.password_doesnt_match));
                return;
            }
            progress.setVisibility(View.VISIBLE);
            final RegistrationSendModel model =
                    new RegistrationSendModel("", phone.getText().toString(), "", password);
//            model.setGovernmant(gov.getText().toString());
//            model.setArea(selectedArea);
//            model.setAvenue(avenue.getText().toString());
//            model.setBlock(block.getText().toString());
//            model.setStreet(street.getText().toString());
//            model.setRemarkaddress(remarkAddress.getText().toString());
//            model.setHouse_no(houseNo.getText().toString());
//            model.setDatebirth(StaticMembers.getDate(calendar));
//            model.setLat(slat);
//            model.setLon(slong);
//            model.setGender(genderRadio.getCheckedRadioButtonId() == R.id.male ? StaticMembers.MALE : StaticMembers.FEMALE);
            Call<RegistrationResponse> call = RetrofitModel.getApi(this).register(model);
            call.enqueue(new CallbackRetrofit<RegistrationResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                    progress.setVisibility(View.GONE);
                    RegistrationResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        StaticMembers.toastMessageShortSuccess(getBaseContext(), result.getMessage());
                        PrefManager.getInstance(getBaseContext()).setAPIToken(result.getData().getToken());
                        PrefManager.getInstance(getBaseContext()).setObject(StaticMembers.USER, result.getData().getUser());
                        StaticMembers.startActivityOverAll(RegistrationActivity.this, MainActivity.class);
                    } else {
                        try {
                            ErrorRegistrationResponse errorLoginResponse = null;
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorRegistrationResponse.class);
                                if (errorLoginResponse != null && errorLoginResponse.getMessage() != null) {
                                    StaticMembers.toastMessageShortFailed(getBaseContext(), errorLoginResponse.getMessage());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    progress.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}