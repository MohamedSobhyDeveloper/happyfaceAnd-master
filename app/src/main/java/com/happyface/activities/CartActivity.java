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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.GsonBuilder;
import com.happyface.R;
import com.happyface.adapters.CartAdapter;
import com.happyface.baseactivity.BaseActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.Loading;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RecyclerItemTouchHelper;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.area_models.AreaResponse;
import com.happyface.models.area_models.DataItem;
import com.happyface.models.cart.AddCartResponse;
import com.happyface.models.cart.CartResponse;
import com.happyface.models.cart.Data;
import com.happyface.models.gifts.messages.MessageGiftResponse;
import com.happyface.models.login_models.EditNameResponse;
import com.happyface.models.login_models.ErrorLoginResponse;
import com.happyface.models.login_models.User;
import com.happyface.models.storeorder.ModelStoreOrder;
import com.sdsmdg.tastytoast.TastyToast;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    BottomSheetDialog dialog;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);


        initialize();

        setupBottomSheet();


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

    private void initialize() {
        loading=new Loading(this);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cartData = new Data();
        cartData.setCart(new ArrayList<>());
        adapter = new CartAdapter(this, cartData, loading);
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

        checkout.setOnClickListener(view -> {
//                OpenAddressDialog();
            dialog.show();
        });

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



     //region Open Dialog View

    private void setupBottomSheet() {

        @SuppressLint("InflateParams") View modalbottomsheet = getLayoutInflater().inflate(R.layout.create_modal_popup, null);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(modalbottomsheet);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        TextView btn_cancel = modalbottomsheet.findViewById(R.id.tv_cancel);
        Button btneditProfile = modalbottomsheet.findViewById(R.id.edit_profile_btn);
        Button btnCheckout = modalbottomsheet.findViewById(R.id.checkout_btn);
        btn_cancel.setOnClickListener(view -> dialog.hide());

        btneditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddressDialog();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = (User) PrefManager.getInstance(CartActivity.this).getObject(USER, User.class);
                if (user.getArea()!=null&&user.getBlock()!=null&&user.getStreet()!=null&&user.getHouse_no()!=null){
                    CallCheckOut();

                }else {
                    StaticMembers.toastMessageShortFailed(CartActivity.this,getString(R.string.enter_your_address));

                }
            }
        });

    }




    @SuppressLint({"SetTextI18n", "StringFormatMatches"})
    public void OpenAddressDialog() {
        user = (User) PrefManager.getInstance(this).getObject(USER, User.class);
        dialogview = new Dialog(this);
        dialogview.setContentView(R.layout.popup_address);
        dialogview.setCanceledOnTouchOutside(true);
        dialogview.setCancelable(true);
        Objects.requireNonNull(dialogview.getWindow()).setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        Button savebtn = dialogview.findViewById(R.id.savebtn);
        Button cancelbtn = dialogview.findViewById(R.id.cancelbtn);
        TextInputEditText block = dialogview.findViewById(R.id.block);
        TextInputEditText street = dialogview.findViewById(R.id.street);
        TextInputEditText avenue = dialogview.findViewById(R.id.avenue);
        TextInputEditText remarkAddress = dialogview.findViewById(R.id.remarkAddress);
        TextInputEditText houseNo = dialogview.findViewById(R.id.houseNo);
        TextInputEditText floorNo = dialogview.findViewById(R.id.floorNo);
        TextInputEditText flatNo = dialogview.findViewById(R.id.flatNo);

        currentLocation = dialogview.findViewById(R.id.currentLocation);
//         User user=new User();
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

        if (user.getFloor()!=null){
            floorNo.setText(user.getFloor()+"");

        }
        if (user.getFlat()!=null){
            flatNo.setText(user.getFlat()+"");
        }

        if (user.getLat()!=null&&!user.getLat().equals("0")){
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
                if (
                        StaticMembers.CheckTextInputEditText(block, getString(R.string.block_empty))&&
                        StaticMembers.CheckTextInputEditText(street, getString(R.string.street_empty))&&
                        StaticMembers.CheckTextInputEditText(houseNo, getString(R.string.houseno_empty))
                ){
                    if (!currentLocationvalue.equals("")){
                        User newuser =new User();
                        newuser.setArea(selectedArea);
                        newuser.setAvenue(avenue.getText().toString()+"");
                        newuser.setBlock(block.getText().toString()+"");
                        newuser.setGovernmant("");
                        newuser.setHouse_no(houseNo.getText().toString()+"");
                        newuser.setEmail(user.getEmail());
                        newuser.setId(user.getId());
                        newuser.setLanguage(user.getLanguage());
                        newuser.setLat(slat+"");
                        newuser.setLon(slong+"");
                        newuser.setName(user.getName());
                        newuser.setRemarkaddress(remarkAddress.getText().toString()+"");
                        newuser.setStreet(street.getText().toString()+"");
                        newuser.setTelephone(user.getTelephone());
                        newuser.setFloor(floorNo.getText().toString()+"");
                        newuser.setFlat(flatNo.getText().toString()+"");

//                        startActivity(new Intent(getBaseContext(), ConfirmBillActivity.class));
                        UpdateAddressDetails(newuser);
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



    //endregion


    //region  Call Api

    public void getCart() {
        if (loading!=null){
            loading.show();
        }
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
//            openLogin(this);
//            finish();
            StaticMembers.startActivityOverAll(this, LogInActivity.class);

        } else {
            Call<CartResponse> call = RetrofitModel.getApi(getBaseContext()).getCart();
            call.enqueue(new CallbackRetrofit<CartResponse>(this) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<CartResponse> call, @NotNull Response<CartResponse> response) {
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        cartData = response.body().getData();
                        adapter.setCartData(cartData);
                        adapter.notifyDataSetChanged();
                        total.setText(getString(R.string.f_kwd)+" "+cartData.getTotal());
                        if (cartData.getTotal()>0){
                            noItem.setVisibility(View.GONE);
                            chekoutLayout.setVisibility(View.VISIBLE);
                        }else {
                            noItem.setVisibility(View.VISIBLE);
                            chekoutLayout.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                }
            });
        }
    }

    private void UpdateAddressDetails(User user) {
        if (loading!=null){
            loading.show();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(StaticMembers.GOV, user.getGovernmant());
        params.put(StaticMembers.BLOCK, user.getBlock());
        params.put(StaticMembers.STREET, user.getStreet());
        params.put(StaticMembers.AVENUE, user.getAvenue());
        params.put(StaticMembers.REMARK_ADDRESS, user.getRemarkaddress());
        params.put(StaticMembers.HOUSE_NO, user.getHouse_no());
        params.put(StaticMembers.AREA, user.getArea());
        params.put(StaticMembers.LAT_, user.getLat());
        params.put(StaticMembers.LON, user.getLon());
        params.put(StaticMembers.floor, user.getFloor());
        params.put(StaticMembers.flat, user.getFlat());

        Call<EditNameResponse> call = RetrofitModel.getApi(this).editField(params);
        call.enqueue(new CallbackRetrofit<EditNameResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<EditNameResponse> call, @NotNull Response<EditNameResponse> response) {
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();
                }
                if (response.isSuccessful()) {
                    EditNameResponse result = response.body();
                    if (result != null) {
                        if (result.isStatus()) {
//                            PrefManager.getInstance(CartActivity.this).setAPIToken(result.getData().getToken());
                            PrefManager.getInstance(CartActivity.this).setObject(USER, result.getData().getUser());
//                            PrefManager.getInstance(getBaseContext()).setObject(StaticMembers.USER, user);
                        }
                        StaticMembers.toastMessageShortSuccess(CartActivity.this, result.getMessage());
                        dialogview.dismiss();
                    }
                }else {
                    StaticMembers.toastMessageShortSuccess(CartActivity.this, "Data Updated Successfully");
                    dialogview.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<EditNameResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();
                }
            }
        });

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
        if (loading!=null){
            loading.show();

        }
        Call<AreaResponse> call = RetrofitModel.getApi(this).getAreas();
        call.enqueue(new CallbackRetrofit<AreaResponse>(this) {
            @Override
            public void onResponse(@NotNull Call<AreaResponse> call, @NotNull Response<AreaResponse> response) {
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }
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
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }
            }
        });
    }


    private void CallCheckOut() {

        if (loading!=null){
            loading.show();

        }
        HashMap<String, String> params = new HashMap<>();
        params.put("code", "0");
        params.put("vip", "0");

        Call<ModelStoreOrder> call = RetrofitModel.getApi(this).storeOrder(params);
        call.enqueue(new CallbackRetrofit<ModelStoreOrder>(this) {
            @Override
            public void onResponse(@NotNull Call<ModelStoreOrder> call, @NotNull Response<ModelStoreOrder> response) {
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }
                if (response.isSuccessful()) {
                    ModelStoreOrder result = response.body();
                    if (result != null) {
                        if (result.getStatus()) {
                            StaticMembers.toastMessageShortSuccess(CartActivity.this, result.getMessage());
                            startActivity(new Intent(CartActivity.this,ConfirmBillActivity.class));
                            finish();

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelStoreOrder> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }
            }
        });
    }



    //endregion




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



    @Override
    public void onBackPressed() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }else {
            finish();
        }
    }


}
