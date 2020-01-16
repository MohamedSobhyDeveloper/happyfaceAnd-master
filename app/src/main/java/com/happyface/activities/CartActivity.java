package com.happyface.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.happyface.R;
import com.happyface.adapters.CartAdapter;
import com.happyface.baseactivity.BaseActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RecyclerItemTouchHelper;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.area_models.AreaResponse;
import com.happyface.models.area_models.DataItem;
import com.happyface.models.cart.CartResponse;
import com.happyface.models.cart.Data;
import com.happyface.models.login_models.User;
import com.sdsmdg.tastytoast.TastyToast;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.happyface.helpers.StaticMembers.USER;

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

    String selectedArea="";
    double slat, slong;
    Button currentLocation;
    String currentLocationvalue="";
    User user;
    Dialog dialogview;
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
        user = (User) PrefManager.getInstance(this).getObject(USER, User.class);

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

    @SuppressLint({"SetTextI18n", "StringFormatMatches"})
    public void OpenAddressDialog() {
        dialogview = new Dialog(this);
        dialogview.setContentView(R.layout.popup_address);
        dialogview.setCanceledOnTouchOutside(true);
        dialogview.setCancelable(true);
        Objects.requireNonNull(dialogview.getWindow()).setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        Button savebtn = dialogview.findViewById(R.id.savebtn);
        Button cancelbtn = dialogview.findViewById(R.id.cancelbtn);
        TextInputEditText gov = dialogview.findViewById(R.id.gov);
        TextInputEditText block = dialogview.findViewById(R.id.block);
        TextInputEditText street = dialogview.findViewById(R.id.street);
        TextInputEditText avenue = dialogview.findViewById(R.id.avenue);
        TextInputEditText remarkAddress = dialogview.findViewById(R.id.remarkAddress);
        TextInputEditText houseNo = dialogview.findViewById(R.id.houseNo);
         currentLocation = dialogview.findViewById(R.id.currentLocation);
//         User user=new User();
        if (user.getGovernmant()!=null){
            gov.setText(user.getGovernmant()+"");
        }
        if (user.getBlock()!=null){
            block.setText(user.getBlock()+"");
        }

        if (user.getStreet()!=null){
            street.setText(user.getStreet()+"");

        }
        if (user.getAvenue()!=null){
            avenue.setText(user.getAvenue()+"");
        }
        if (user.getRemarkaddress()!=null){
            remarkAddress.setText(user.getRemarkaddress()+"");

        }
        if (user.getHouse_no()!=null){
            houseNo.setText(user.getHouse_no()+"");

        }
        if (user.getLat()!=null){
            currentLocation.setText(getString(R.string.current_location_ss)+ user.getLat()+","+ user.getLon());
            slat= Double.parseDouble(user.getLat());
            slong= Double.parseDouble(user.getLon());
            currentLocationvalue=slat+slong+"";
        }




        AppCompatSpinner area=dialogview.findViewById(R.id.area);


        //region get area

        List<String> areas=new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_list_item_1, areas);
        area.setAdapter(adapter);
        getAreas(areas, adapter,area);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedArea = areas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        //endregion



     currentLocation.setOnClickListener(view -> {
         Intent intent = new Intent(CartActivity.this, MapsActivity.class);
         intent.putExtra(StaticMembers.LAT, slat);
         intent.putExtra(StaticMembers.LONG, slong);
         startActivityForResult(intent, StaticMembers.LOCATION_CODE);
     });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StaticMembers.CheckTextInputEditText(gov, getString(R.string.phone_empty))&&
                        StaticMembers.CheckTextInputEditText(block, getString(R.string.phone_empty))&&
                        StaticMembers.CheckTextInputEditText(street, getString(R.string.phone_empty))&&
                        StaticMembers.CheckTextInputEditText(avenue, getString(R.string.phone_empty))&&
                        StaticMembers.CheckTextInputEditText(remarkAddress, getString(R.string.phone_empty))&&
                        StaticMembers.CheckTextInputEditText(houseNo, getString(R.string.phone_empty))
                ){
                    if (!currentLocationvalue.equals("")){
                        User newuser =new User();
                        newuser.setArea(selectedArea);
                        newuser.setAvenue(avenue.getText().toString());
                        newuser.setBlock(block.getText().toString());
                        newuser.setGovernmant(gov.getText().toString());
                        newuser.setHouse_no(houseNo.getText().toString());
                        newuser.setEmail(user.getEmail());
                        newuser.setId(user.getId());
                        newuser.setLanguage(user.getLanguage());
                        newuser.setLat(slat+"");
                        newuser.setLon(slong+"");
                        newuser.setName(user.getName());
                        newuser.setRemarkaddress(remarkAddress.getText().toString());
                        newuser.setStreet(street.getText().toString());
                        newuser.setTelephone(user.getTelephone());
                        PrefManager.getInstance(getBaseContext()).setObject(StaticMembers.USER, newuser);

                        startActivity(new Intent(getBaseContext(), ConfirmBillActivity.class));
                    }else {
                        TastyToast.makeText(CartActivity.this,getString(R.string.choose_current_location),TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    }
                }
            }
        });


        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogview.cancel();
            }
        });


//        dialogview.show();
    }


    private void getAreas(List<String> list, ArrayAdapter<String> adapter, AppCompatSpinner area) {
//        AreaResponse areaResponse = (AreaResponse) PrefManager.getInstance(this).getObject(StaticMembers.AREA, AreaResponse.class);
//        if (areaResponse != null) {
//            list.clear();
//            for (DataItem dataItem : areaResponse.getData()) {
//                if (dataItem.getName() != null)
//                    list.add(dataItem.getName());
//            }
//            adapter.notifyDataSetChanged();
//        }
        avi.setVisibility(View.VISIBLE);
        Call<AreaResponse> call = RetrofitModel.getApi(this).getAreas();
        call.enqueue(new CallbackRetrofit<AreaResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<AreaResponse> call, @NotNull Response<AreaResponse> response) {
                avi.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    list.clear();
                    for (DataItem dataItem : response.body().getData()) {
                        if (dataItem.getName() != null)
                            list.add(dataItem.getName());
                    }
                    PrefManager.getInstance(getApplicationContext()).setObject(StaticMembers.AREA, response.body());
                    adapter.notifyDataSetChanged();
                    if (user.getArea()!=null&&!user.getArea().equals("")){
                        for (int i=0;i<list.size();i++){
                            if (list.get(i).equals(user.getArea())){
                                area.setSelection(i);
                            }
                        }
                    }

                    dialogview.show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AreaResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                avi.setVisibility(View.GONE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == StaticMembers.LOCATION_CODE && data != null) {
                slat = data.getDoubleExtra(StaticMembers.LAT, 0);
                slong = data.getDoubleExtra(StaticMembers.LONG, 0);
                currentLocation.setText(String.format(Locale.getDefault(), getString(R.string.current_location_s), slat, slong));
                currentLocationvalue=String.format(Locale.getDefault(), getString(R.string.current_location_s), slat, slong);
            }
        }
    }
}
