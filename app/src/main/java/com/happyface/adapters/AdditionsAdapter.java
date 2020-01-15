package com.happyface.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.happyface.R;
import com.happyface.models.gifts.additional.DataItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditionsAdapter extends RecyclerView.Adapter<AdditionsAdapter.Holder> {
    private Context context;
    private List<DataItem> list;
    private OnSelectedListener listener;
    private int checkedIndex = -1;
    private boolean cardClick = false;
    int amount;

    public int getCheckedIndex() {
        return checkedIndex;
    }

    public AdditionsAdapter(Context context, List<DataItem> list, OnSelectedListener onSelectedListener) {
        this.context = context;
        this.list = list;
        this.listener = onSelectedListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_addition, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem additional = list.get(position);
        holder.name.setText(additional.getName());
        holder.price.setText(String.format(Locale.getDefault(), context.getString(R.string.s_kwd), additional.getPrice()));
        Glide.with(context).load(additional.getImage()).into(holder.image);
        holder.checkbox.setChecked(additional.isSelected());
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed() || cardClick) {
                additional.setSelected(isChecked);
                listener.onClick(position);
                cardClick = false;
            }
        });
        holder.cardImage.setOnClickListener(v -> {
            cardClick = true;
            holder.checkbox.performClick();
        });

        amount = additional.getAmount();
        amount = amount == 0 ? 1 : amount;
        holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount));
        if (amount < 2)
            holder.remove.setEnabled(false);
        holder.add.setOnClickListener(v -> {
            amount = additional.getAmount();
            amount = amount == 0 ? 1 : amount;
            amount++;
            holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount));
            additional.setAmount(amount);
            listener.onClick(position);
            if (amount > 1)
                holder.remove.setEnabled(true);
        });
        holder.remove.setOnClickListener(v -> {
            amount = additional.getAmount();
            amount = amount == 0 ? 1 : amount;
            if (amount < 2)
                holder.remove.setEnabled(false);
            else {
                amount--;
                additional.setAmount(amount);
                listener.onClick(position);
                holder.amountText.setText(String.format(Locale.getDefault(), "%d", amount));
                if (amount < 2)
                    holder.remove.setEnabled(false);
            }
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
        @BindView(R.id.quantity)
        TextView amountText;
        @BindView(R.id.checkbox)
        CheckBox checkbox;
        @BindView(R.id.remove)
        ImageButton remove;
        @BindView(R.id.add)
        ImageButton add;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSelectedListener {
        public void onClick(int pos);
    }
}
