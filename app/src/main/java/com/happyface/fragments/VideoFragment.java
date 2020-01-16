package com.happyface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.happyface.R;
import com.happyface.helpers.StaticMembers;
import com.wang.avi.AVLoadingIndicatorView;

public class VideoFragment extends Fragment {

    private String url;
    private VideoView video;
    private AVLoadingIndicatorView progress;
    private boolean isPlaying, isFirst;
    private int stopPosition = 0;
    private View clicker;

    public static VideoFragment getInstance(String url) {
        VideoFragment f = new VideoFragment();
        f.url = url;
        return f;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(StaticMembers.VIDEO, url);
        outState.putInt(StaticMembers.STOP, stopPosition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.item_video_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(StaticMembers.VIDEO);
            stopPosition = savedInstanceState.getInt(StaticMembers.STOP);
        }
        video = view.findViewById(R.id.video);
        clicker = view.findViewById(R.id.clicker);
        progress = view.findViewById(R.id.avi);
        video.setVideoPath(url);
        isPlaying = false;
        isFirst = true;
        video.setOnPreparedListener(mp -> {
            progress.setVisibility(View.GONE);
            if (isPlaying) {
                video.seekTo(stopPosition);
                video.postDelayed(() -> video.start(), 200);
            }
            isFirst = false;
        });
        resumeVideo();
        clicker.setOnClickListener(v -> {
            progress.setVisibility(View.VISIBLE);
            if (isPlaying) {
                progress.setVisibility(View.GONE);
                pauseVideo();
            } else
                video.resume();
            isPlaying = !isPlaying;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    public void pauseVideo() {
        if (video != null) {
            stopPosition = video.getCurrentPosition();
            video.pause();
        }
    }

    public void resumeVideo() {
        if (video != null) {
            progress.setVisibility(View.VISIBLE);
            isPlaying = true;
            video.resume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (video != null && !isFirst) {
            video.seekTo(stopPosition);
            video.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (video != null)
            video.resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (video != null)
            video.stopPlayback();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (video != null)
            video.stopPlayback();
    }
}
