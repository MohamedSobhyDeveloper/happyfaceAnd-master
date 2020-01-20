package com.happyface.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.happyface.R;
import com.happyface.models.category_models.DataItem;
import com.happyface.models.search_products.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private Context context;
    private List<DataItem> list;
    private OnCategorySelectedListener listener;

    public CategoryAdapter(Context context, List<DataItem> list, OnCategorySelectedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem category = list.get(position);
        holder.name.setText(category.getName());
//        Glide.with(context).load(category.getImageIcon()).into(holder.image);
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(170, 130))
                .load(category.getImageIcon())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> listener.onClick(""+category.getId()));
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

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCategorySelectedListener {
        public void onClick(String catId);
    }
}
