package com.hrtzpi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hrtzpi.R;
import com.hrtzpi.adapters.VideosAdapter;
import com.hrtzpi.helpers.CallbackRetrofit;
import com.hrtzpi.helpers.Loading;
import com.hrtzpi.helpers.PrefManager;
import com.hrtzpi.helpers.RetrofitModel;
import com.hrtzpi.helpers.StaticMembers;
import com.hrtzpi.models.video_models.DataItem;
import com.hrtzpi.models.video_models.VideoResponse;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class VideoListFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.noItem)
    TextView noItem;
    VideosAdapter adapter;
    List<DataItem> productList;
    int page = 1, maxPage;
    boolean reachBottom;
    private boolean isRefresh;
    HashMap<String, String> params;
    Loading  loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        loading=new Loading(getActivity());
        productList = new ArrayList<>();
        adapter = new VideosAdapter(getContext(), productList, loading);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !reachBottom && page < maxPage) {
                    reachBottom = true;
                    page++;
                    getVideos();
                }
            }
        });
        getVideos();
        swipe.setOnRefreshListener(this::getVideos);
    }

    public void getVideos() {
        getVideos(true);
    }

    public void getVideos(boolean isRef) {
        if (loading!=null){
            loading.show();

        }
        noItem.setVisibility(View.GONE);
        Call<VideoResponse> call = RetrofitModel.getApi(getActivity()).getVideoList();
        call.enqueue(new CallbackRetrofit<VideoResponse>(getActivity()) {
            @Override
            public void onResponse(@NotNull Call<VideoResponse> call, @NotNull Response<VideoResponse> response) {
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }

                swipe.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    maxPage = response.body().getData().getLastPage();
                    if (isRefresh || isRef)
                        productList.clear();
                    isRefresh = false;
                    productList.addAll(response.body().getData().getData());
                    adapter.notifyDataSetChanged();
                    if (productList.isEmpty())
                        noItem.setVisibility(View.VISIBLE);
                    else noItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<VideoResponse> call, @NotNull Throwable t) {
                super.onFailure(call, t);
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();

                }

                swipe.setRefreshing(false);
                noItem.setVisibility(View.GONE);
            }
        });
    }
}
