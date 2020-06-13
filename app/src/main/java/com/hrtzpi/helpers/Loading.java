package com.hrtzpi.helpers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;

import com.hrtzpi.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by ksi on 27-Mar-18.
 */

public class Loading extends AppCompatDialog {
    public Loading(Context context) {
        super(context);
        setup();
    }

    @SuppressWarnings("ConstantConditions")
    private void setup(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.progress_layout);
        setCancelable(false);

        AVLoadingIndicatorView ivLoading = findViewById(R.id.avi);

    }
}
