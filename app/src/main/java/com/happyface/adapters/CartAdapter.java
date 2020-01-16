package com.happyface.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.happyface.R;
import com.happyface.activities.CartActivity;
import com.happyface.activities.LogInActivity;
import com.happyface.fragments.GiftsFragment;
import com.happyface.helpers.CallbackRetrofit;
import com.happyface.helpers.PrefManager;
import com.happyface.helpers.RetrofitModel;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.cart.AddCartResponse;
import com.happyface.models.cart.CartItem;
import com.happyface.models.cart.Data;
import com.happyface.models.cart.GiftAdditionalItem;
import com.happyface.models.cart.delete_cart_models.DeleteCartResponse;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {

    private CartActivity activity;
    private AVLoadingIndicatorView progress;
    private Data cartData;

    public CartAdapter(CartActivity activity, Data cartData, AVLoadingIndicatorView progress) {
        this.activity = activity;
        this.cartData = cartData;
        this.progress = progress;
    }

    void changeTotal(double total) {
        cartData.setTotal(total);
        notifyItemChanged(cartData.getCart().size() + 1);
    }

    public void setCartData(Data cartData) {
        this.cartData = cartData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(activity).inflate(R.layout.item_product_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position < cartData.getCart().size()) {
            holder.totalLayout.setVisibility(View.GONE);
            holder.itemLayout.setVisibility(View.VISIBLE);
            CartItem cartItem = cartData.getCart().get(position);

            if (cartItem.getProduct() != null)
                Glide.with(activity).load(cartItem.getProduct().getLogo()).into(holder.image);

            if (cartItem.getAdditionalPrice() != 0) {
                holder.additionalLayout.setVisibility(View.VISIBLE);
                holder.priceAdditional.setText(String.format(Locale.getDefault(), activity.getString(R.string.s_kwd),
                        new DecimalFormat("#.#").format(cartItem.getAdditionalPrice())));
                holder.editAddText.setText(activity.getString(R.string.edit_additionals));
            }

            holder.editAdditional.setOnClickListener(v -> {
                GiftsFragment.getInstance(cartItem).show(activity.getSupportFragmentManager(), "gift");
            });
            holder.name.setText(cartItem.getProduct() != null ? cartItem.getProduct().getName() : "");
            holder.productId.setText(
                    String.format(Locale.getDefault(), activity.getString(R.string.product_id_s), cartItem.getProductId()));
            holder.price.setText(String.format(Locale.getDefault(), activity.getString(R.string.s_kwd), cartItem.getPrice()));
            /*holder.itemView.setOnClickListener(v -> {

            });*/
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.linear.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            holder.linear.setLayoutParams(params);

            int amount = Integer.parseInt(cartItem.getQuantity());
            holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount));
            if (amount < 2)
                holder.remove.setEnabled(false);
            holder.add.setOnClickListener(v -> {
                holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount + 1));
                changeCartItem(cartItem, amount + 1, holder.amountText,holder.avi);
                if (amount + 1 > 1)
                    holder.remove.setEnabled(true);
            });
            holder.remove.setOnClickListener(v -> {
                if (amount < 2)
                    holder.remove.setEnabled(false);
                else {
                    holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount - 1));
                    changeCartItem(cartItem, amount - 1, holder.amountText,holder.avi);
                    if (amount - 1 < 2)
                        holder.remove.setEnabled(false);
                }
            });
        } else {
            holder.totalLayout.setVisibility(View.VISIBLE);
            holder.itemLayout.setVisibility(View.GONE);
            holder.total.setText(String.format(Locale.getDefault(), activity.getString(R.string.f_kwd), cartData.getTotal()));
        }
    }

    private void changeCartItem(CartItem cartItem, int amount, TextView amountText,AVLoadingIndicatorView avi) {
        // progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            List<String> addIDs = new ArrayList<>();
            List<String> addQuantities = new ArrayList<>();
            if (cartItem.getGiftAdditional() != null)
                for (GiftAdditionalItem addition : cartItem.getGiftAdditional()) {
                    addIDs.add("" + addition.getId());
                    addQuantities.add(addition.getQuantity());
                }
            Map<String, String> params = new HashMap<>();
            params.put(StaticMembers.PRODUCT_ID, cartItem.getProductId());
            params.put(StaticMembers.QUANTITY, "" + amount);
            params.put(StaticMembers.GIFT_COVERS_ID, "" + cartItem.getGiftsCovers());
            if (cartItem.getGiftMessegsFrom() == null)
                params.remove(StaticMembers.GIFT_MESSAGE_FROM);
            else
                params.put(StaticMembers.GIFT_MESSAGE_FROM, cartItem.getGiftMessegsFrom());
            if (cartItem.getGiftMessegsTo() == null)
                params.remove(StaticMembers.GIFT_MESSAGE_TO);
            else
                params.put(StaticMembers.GIFT_MESSAGE_TO, cartItem.getGiftMessegsTo());
            if (cartItem.getGiftMessegs() == 0)
                params.remove(StaticMembers.GIFT_MESSAGE_ID);
            else
                params.put(StaticMembers.GIFT_MESSAGE_ID, "" + cartItem.getGiftMessegs());
            if (cartItem.getGiftMessegsMsg() == null)
                params.remove(StaticMembers.GIFT_MESSAGE_TEXT);
            else
                params.put(StaticMembers.GIFT_MESSAGE_TEXT, cartItem.getGiftMessegsMsg());
//            avi.setVisibility(View.VISIBLE);
            Call<AddCartResponse> call = RetrofitModel.getApi(activity)
                    .addOrEditCart(params, addIDs, addQuantities);
            call.enqueue(new CallbackRetrofit<AddCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<AddCartResponse> call, @NotNull Response<AddCartResponse> response) {
                    // progress.setVisibility(View.GONE);
//                    avi.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart();
                }

                @Override
                public void onFailure(@NotNull Call<AddCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    //  progress.setVisibility(View.GONE);
                }
            });
        }
    }

    public void deleteCartItem(int position) {
        CartItem cartItem = cartData.getCart().get(position);
        removeItem(position);
        // progress.setVisibility(View.VISIBLE);
        if (PrefManager.getInstance(activity).getAPIToken().isEmpty()) {
            Intent intent = new Intent(activity, LogInActivity.class);
            intent.putExtra(StaticMembers.ACTION, true);
            activity.startActivity(intent);
        } else {
            Call<DeleteCartResponse> call = RetrofitModel.getApi(activity).deleteCartItem(cartItem.getProductId());
            call.enqueue(new CallbackRetrofit<DeleteCartResponse>(activity) {
                @Override
                public void onResponse(@NotNull Call<DeleteCartResponse> call, @NotNull Response<DeleteCartResponse> response) {
                    // progress.setVisibility(View.GONE);
                    if (!response.isSuccessful()) {
                        restoreItem(cartItem, position);
                        if (!response.message().isEmpty())
                            StaticMembers.toastMessageShortSuccess(activity, response.message());
                        StaticMembers.checkLoginRequired(response.errorBody(), activity);
                    } else activity.getCart();
                }

                @Override
                public void onFailure(@NotNull Call<DeleteCartResponse> call, @NotNull Throwable t) {
                    super.onFailure(call, t);
                    // progress.setVisibility(View.GONE);
                }
            });
        }
    }

    private void removeItem(int position) {
        cartData.getCart().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartData.getCart().size());
    }

    private void restoreItem(CartItem item, int position) {
        cartData.getCart().add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return cartData.getCart().size() + 1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.productId)
        TextView productId;
        @BindView(R.id.total)
        TextView total;
        @BindView(R.id.front)
        public LinearLayout front;
        @BindView(R.id.itemLayout)
        FrameLayout itemLayout;
        @BindView(R.id.totalLayout)
        LinearLayout totalLayout;
        @BindView(R.id.linear)
        RelativeLayout linear;
        @BindView(R.id.additionalLayout)
        LinearLayout additionalLayout;

        @BindView(R.id.remove)
        ImageButton remove;
        @BindView(R.id.add)
        ImageButton add;

        @BindView(R.id.quantity)
        TextView amountText;
        @BindView(R.id.priceAdditional)
        TextView priceAdditional;
        @BindView(R.id.editAddText)
        TextView editAddText;
        @BindView(R.id.editAdditional)
        CardView editAdditional;
        @BindView(R.id.avi)
        AVLoadingIndicatorView avi;
        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
