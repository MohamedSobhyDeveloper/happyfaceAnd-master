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
import com.google.gson.GsonBuilder;
import com.happyface.R;
import com.happyface.activities.ProductDetailsActivity;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
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
    private AVLoadingIndicatorView progress;
    private RecyclerView recycler;
    private Activity activity;

    public FavoritesAdapter(Context context, List<Product> list, AVLoadingIndicatorView progress, RecyclerView recycler, Activity activity) {
        this.context = context;
        this.list = list;
        this.recycler = recycler;
        this.progress = progress;
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
            Glide.with(context).load(product.getLogo()).into(holder.image);
        holder.favorite.setChecked(product.isWishlist());
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                changeFavorite(position, product.getId(), buttonView);
            }
        });
        holder.name.setText(product.getName());
        if (product.getPrice() != null)
            holder.price.setText(product.getPrice());
        holder.productId.setText(product.getProductNo());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra(StaticMembers.PRODUCT, product);
            context.startActivity(intent);
        });
    }


    private void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    private void changeFavorite(int position, long id, CompoundButton buttonView) {
        progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(getBaseContext()).getAPIToken().isEmpty()) {
            StaticMembers.openLogin(context);
        } else {
            Call<WishlistResponse> call = RetrofitModel.getApi(context).toggleWishlist(id);
            call.enqueue(new CallbackRetrofit<WishlistResponse>(context) {
                @Override
                public void onResponse(@NotNull Call<WishlistResponse> call, @NotNull Response<WishlistResponse> response) {
                    progress.setVisibility(View.GONE);
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
                    progress.setVisibility(View.GONE);
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

        @BindView(R.id.productId)
        TextView productId;

        @BindView(R.id.favorite)
        CheckBox favorite;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
