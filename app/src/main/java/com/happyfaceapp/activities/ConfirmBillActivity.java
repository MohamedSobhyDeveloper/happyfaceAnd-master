package com.happyfaceapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.happyfaceapp.R;
import com.happyfaceapp.baseactivity.BaseActivity;
import com.happyfaceapp.helpers.StaticMembers;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmBillActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home)
    CardView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bill);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        home.setOnClickListener(v -> StaticMembers.startActivityOverAll(ConfirmBillActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
