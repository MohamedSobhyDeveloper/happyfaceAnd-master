package com.happyface.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.GsonBuilder;
import com.happyface.R;
import com.happyface.activities.CartActivity;
import com.happyface.activities.LogInActivity;
import com.happyface.activities.ProductDetailsActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.Loading;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.cart.AddCartResponse;
import com.happyface.models.search_products.Product;
import com.happyface.models.wishlist_models.ErrorWishListResponse;
import com.happyface.models.wishlist_models.WishlistResponse;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Holder> {
    private Context context;
    private List<Product> list;
    private Loading loading;
    private RecyclerView recycler;
    private Activity activity;

    public FavoritesAdapter(Context context, List<Product> list, Loading loading, RecyclerView recycler, Activity activity) {
        this.context = context;
        this.list = list;
        this.recycler = recycler;
        this.loading = loading;
        this.activity=activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product product = list.get(position);
        if (product.getLogo() != null)
//            Glide.with(context).load(product.getLogo()).into(holder.image);
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(170, 130))
                .load(product.getLogo())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.image);
        holder.favorite.setChecked(product.isWishlist());
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                changeFavorite(position, product.getId(), buttonView);
            }
        });
        holder.name.setText(product.getName());
        if (product.getPrice() != null)
            holder.price.setText(product.getPrice());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra(StaticMembers.PRODUCT, product);
            context.startActivity(intent);
        });


        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCartItem(position);
            }
        });
    }

    private void changeCartItem(int position) {
        // progress.setVisibility(View.VISIBLE);
        if (loading!=null){
            loading.show();

        }

        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            if (loading!=null&&loading.isShowing()){
                loading.dismiss();
            }
            Intent intent = new Intent(getBaseContext(), LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            context.startActivity(intent);
        } else {

            Call<AddCartResponse> call = RetrofitModel.getApi(context).addOrEditCart("" + list.get(position).getId(),1);
            call.enqueue(new CallbackRetrofit<AddCartResponse>(context) {
                @Override
                public void onResponse(@NotNull Call<AddCartResponse> call, @NotNull Response<AddCartResponse> response) {
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                    if (!response.isSuccessful()) {
                        //amountText.setText(String.format(Locale.getDefault(), "%d", amount - 1));
                        StaticMembers.checkLoginRequired(response.errorBody(), getBaseContext(),activity);
                    } else if (response.body() != null) {
                        context.startActivity(new Intent(getBaseContext(), CartActivity.class));
                        StaticMembers.toastMessageShortSuccess(getBaseContext(), response.body().getMessage());
                        //amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }
                }
            });
        }
    }

    private void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    private void changeFavorite(int position, long id, CompoundButton buttonView) {
        if (loading!=null){
            loading.show();

        }
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            StaticMembers.openLogin(context);
        } else {
            Call<WishlistResponse> call = RetrofitModel.getApi(context).toggleWishlist(id);
            call.enqueue(new CallbackRetrofit<WishlistResponse>(context) {
                @Override
                public void onResponse(@NotNull Call<WishlistResponse> call, @NotNull Response<WishlistResponse> response) {
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }


                    WishlistResponse result = response.body();
                    if (response.isSuccessful() && result != null) {
                        if (!result.getData().isWishlist()) {
                            removeItem(position);
                        }
                        StaticMembers.toastMessageShortSuccess(context, result.getMessage());
                    } else {
                        buttonView.setChecked(!buttonView.isChecked());
                        StaticMembers.checkLoginRequired(response.errorBody(), context,activity);
                        try {
                            ErrorWishListResponse errorLoginResponse = null;
                            if (response.errorBody() != null) {
                                errorLoginResponse = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorWishListResponse.class);
                                if (errorLoginResponse != null) {
                                    StaticMembers.toastMessageShortFailed(context, errorLoginResponse.getMessage());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<WishlistResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    if (loading!=null&&loading.isShowing()){
                        loading.dismiss();

                    }

                }
            });
        }
    }

    private Context getBaseContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.favorite)
        CheckBox favorite;

        @BindView(R.id.addToCart)
        CheckBox addToCart;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
