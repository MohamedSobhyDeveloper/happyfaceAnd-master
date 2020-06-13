package com.hrtzpi.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hrtzpi.R;
import com.hrtzpi.helpers.LoopingPagerAdapter;
import com.hrtzpi.models.slider_models.SliderItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter implements LoopingPagerAdapter {

    private Context context;
    private List<SliderItem> imageList;
    private CategoryAdapter.OnCategorySelectedListener listener;
    private int pos = 0;

    public ImageSliderAdapter(Context context, List<SliderItem> imageList, CategoryAdapter.OnCategorySelectedListener listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }

    public int getPos() {
        return pos;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, container, false);
        ImageView imageView = view.findViewById(R.id.image);

        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .load(imageList.get(pos)
                 .getPhoto())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);

        view.setOnClickListener(v -> listener.onClick(imageList.get(pos).getCategoryId()));
        container.addView(view);
        if (pos >= imageList.size() - 1)
            pos = 0;
        else
            ++pos;
        return view;
    }

    @Override
    public int getCount() {
        return imageList.isEmpty() ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getRealCount() {
        return imageList.size();
    }
}
