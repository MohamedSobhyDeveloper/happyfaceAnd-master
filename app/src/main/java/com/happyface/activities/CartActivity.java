package com.happyface.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.happyface.R;
import com.happyface.adapters.CartAdapter;
import com.happyface.baseactivity.BaseActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RecyclerItemTouchHelper;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.cart.CartResponse;
import com.happyface.models.cart.Data;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.toolbar)
    Toolbar toolbar;/*
    @BindView(R.id.addPromo)
    CardView addPromo;*/
    @BindView(R.id.checkout)
    TextView checkout;
    @BindView(R.id.total)
    TextView total;

    CartAdapter adapter;
    Data cartData;
    @BindView(R.id.chekout_layout)
    LinearLayout chekoutLayout;
    @BindView(R.id.noItem)
    TextView noItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cartData = new Data();
        cartData.setCart(new ArrayList<>());
        adapter = new CartAdapter(this, cartData, avi);
        recycler.setAdapter(adapter);
        getCart();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.START,
                (viewHolder, direction, position) -> {
                    if (cartData.getCart().size() > position) adapter.deleteCartItem(position);
                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler);

//        checkout.setOnClickListener(v ->
//                startActivity(new Intent(getBaseContext(), ConfirmBillActivity.class))
//
//        );

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddressDialog();
            }
        });



       /* addPromo.setOnClickListener(v -> {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            alertDialog.setTitle(getString(R.string.edit_name));
            input.setHint(R.string.add_promocode);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input); // uncomment this line
            alertDialog.setPositiveButton(getString(R.string.ok), (dialog, which) -> setPromo(input.getText().toString()));
            alertDialog.setNegativeButton(getString(R.string.cancel), null);
            alertDialog.show();
        });*/

    }
/*

    public void setPromo(String s) {
        progress.setVisibility(View.VISIBLE);
        Call<MessageResponse> call = RetrofitModel.getApi(this).addPromo(s);
        call.enqueue(new CallbackRetrofit<MessageResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
                progress.setVisibility(View.GONE);
                MessageResponse result;
                if (response.isSuccessful()) {
                    result = response.body();
                    if (result != null) {
                        StaticMembers.toastMessageShort(getBaseContext(), result.getMessage());
                        if (result.isStatus()) {
                            getCart();
                        }
                    }
                } else {
                    StaticMembers.checkLoginRequired(response.errorBody(), CartActivity.this);
                    try {
                        assert response.errorBody() != null;
                        result = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        StaticMembers.toastMessageShort(getBaseContext(), result.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                progress.setVisibility(View.GONE);
            }
        });
    }
*/

    public void getCart() {
        avi.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
//            openLogin(this);
//            finish();
            StaticMembers.startActivityOverAll(this, LogInActivity.class);

        } else {
            Call<CartResponse> call = RetrofitModel.getApi(getBaseContext()).getCart();
            call.enqueue(new CallbackRetrofit<CartResponse>(this) {
                @Override
                public void onResponse(@NotNull Call<CartResponse> call, @NotNull Response<CartResponse> response) {
                    avi.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        chekoutLayout.setVisibility(View.VISIBLE);
                        cartData = response.body().getData();
                        adapter.setCartData(cartData);
                        adapter.notifyDataSetChanged();
                        total.setText(String.format(Locale.getDefault(), getString(R.string.f_kwd), cartData.getTotal()));
                        if (cartData.getTotal()==0){
                            noItem.setVisibility(View.VISIBLE);
                        }
                    } else {
                        StaticMembers.checkLoginRequired(response.errorBody(), CartActivity.this, CartActivity.this);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    avi.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void OpenAddressDialog() {
        Dialog dialogview = new Dialog(this);
        dialogview.setContentView(R.layout.popup_address);
        dialogview.setCanceledOnTouchOutside(true);
        dialogview.setCancelable(true);
        Objects.requireNonNull(dialogview.getWindow()).setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        Button savebtn = dialogview.findViewById(R.id.savebtn);
        Button cancelbtn = dialogview.findViewById(R.id.cancelbtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ConfirmBillActivity.class));
            }
        });


        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogview.cancel();
            }
        });


        dialogview.show();
    }
}
