package com.hrtzpi.baseactivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.hrtzpi.R;
import com.hrtzpi.helpers.StaticMembers;

import java.util.Locale;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("CurrentScreen", this.getClass().getSimpleName());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setuplanguages();

    }



    @Override
    public void onBackPressed() {
        new MaterialStyledDialog.Builder(this)
                .setDescription(R.string.want_to_exit)
                .setPositiveText(R.string.yes)
                .setNegativeText(R.string.no)
                .setIcon(R.drawable.hartlogo)
                .setHeaderColor(R.color.colorPrimary)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                    BaseActivity.this.finish();

                }).onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }



    private void setuplanguages() {
        String language = StaticMembers.getLanguage(this);
        Log.e("mmmm", language);
        if (language.equals("ar")) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        } else {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        }
    }

}
