package com.happyface.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.happyface.R;
import com.happyface.models.gifts.covers.DataItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoversAdapter extends RecyclerView.Adapter<CoversAdapter.Holder> {
    private Context context;
    private List<DataItem> list;
    private OnSelectedListener listener;
    private int checkedIndex = -1;
    private boolean cardClick = false;

    public int getCheckedIndex() {
        return checkedIndex;
    }

    public void setCheckedIndex(int checkedIndex) {
        this.checkedIndex = checkedIndex;
    }

    public CoversAdapter(Context context, List<DataItem> list, OnSelectedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_cover, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem cover = list.get(position);
        holder.name.setText(cover.getName());
        holder.price.setText(String.format(Locale.getDefault(), context.getString(R.string.s_kwd), cover.getPrice()));
//        Glide.with(context).load(cover.getCoverImage()).into(holder.image);
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(140, 140))
                .load(cover.getCoverImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.image);

        // holder.itemView.setOnClickListener(v -> listener.onClick("" + cover.getId()));
        holder.checkbox.setChecked(position == checkedIndex);
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed() || cardClick) {
                if (isChecked) {
                    int t = checkedIndex;
                    checkedIndex = position;
                    listener.onClick(position);
                    notifyItemChanged(t);
                } else {
                    checkedIndex = -1;
                    listener.onClick(checkedIndex);
                }
                cardClick = false;
            }
        });
        holder.cardImage.setOnClickListener(v -> {
            cardClick = true;
            holder.checkbox.performClick();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.cardImage)
        CardView cardImage;

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.checkbox)
        CheckBox checkbox;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSelectedListener {
        public void onClick(int pos);
    }
}
