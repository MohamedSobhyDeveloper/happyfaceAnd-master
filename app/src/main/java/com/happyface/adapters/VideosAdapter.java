package com.happyface.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.happyface.R;
import com.happyface.activities.FullScreenVideoActivity;
import com.happyface.helpers.StaticMembers;
import com.happyface.models.video_models.DataItem;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.Holder> {
    private Context context;
    private List<DataItem> list;
    private AVLoadingIndicatorView progress;

    public VideosAdapter(Context context, List<DataItem> list, AVLoadingIndicatorView progress) {
        this.context = context;
        this.list = list;
        this.progress = progress;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataItem video = list.get(position);

        holder.name.setText(video.getName());
        if (video.getLogo() != null)
            Glide.with(context).load(video.getLogo()).into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullScreenVideoActivity.class);
            intent.putExtra(StaticMembers.VIDEO, video);
            context.startActivity(intent);
          /*  JCVideoPlayerStandard.startFullscreen(context,
                    JCVideoPlayerStandard.class,
                    video.getVideo(),
                    video.getName());*/
        });
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
}
