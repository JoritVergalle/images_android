package com.example.jorit.images_app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>{

    private List<Image> imagesList;

    public class TimelineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        public TextView description;

        public TimelineViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    public TimelineAdapter(List<Image> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item, parent, false);
        return new TimelineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        Image image = imagesList.get(position);
        holder.description.setText(image.getDescription());
        //need to add image yeeeee

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
