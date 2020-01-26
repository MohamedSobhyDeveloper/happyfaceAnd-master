package com.happyfaceapp.helpers;

import android.content.Context;

import com.happyfaceapp.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackRetrofit<T> implements Callback<T> {

    private Context context;

    public CallbackRetrofit(Context context) {
        this.context = context;
    }

    @Override
    public abstract void onResponse(@NotNull Call<T> call, @NotNull Response<T> response);

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        if (context != null)
            StaticMembers.toastMessageShortFailed(context,context.getString(R.string.connection_error));
    }
}
