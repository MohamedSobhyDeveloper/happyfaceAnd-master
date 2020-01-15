package com.happyface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.happyface.R;
import com.happyface.helpers.StaticMembers;

import java.util.Objects;

public class ImageFragment extends Fragment {

    private String url;

    public static ImageFragment getInstance(String url) {
        ImageFragment f = new ImageFragment();
        f.url = url;
        return f;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(StaticMembers.IMAGE, url);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.item_image_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null)
            url = savedInstanceState.getString(StaticMembers.IMAGE);
        ImageView imageView = view.findViewById(R.id.image);
        Glide.with(Objects.requireNonNull(getContext())).load(url).into(imageView);
    }
}
