package com.example.jorit.images_app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Image;
import com.example.jorit.images_app.fragments.TimelineFragment;
import com.example.jorit.images_app.interfaces.ItemLongClickListener;
import com.example.jorit.images_app.interfaces.ItemTouchHelperAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> implements ItemTouchHelperAdapter {

    private List<Image> imagesList;
    private TimelineFragment fragment;

    public class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        @BindView(R.id.description)
        public TextView description;

        @BindView(R.id.picture)
        public ImageView picture;

        ItemLongClickListener itemLongClickListener;

        public TimelineViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

            itemView.setOnLongClickListener(this);
        }

        public void setItemLongClickListener(ItemLongClickListener itemLongClickListener)
        {
            this.itemLongClickListener=itemLongClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return false;
        }
    }

    public TimelineAdapter(List<Image> imagesList) {
        this.imagesList = imagesList;
    }

    public TimelineAdapter(TimelineFragment fragment,List<Image> imagesList ) {
        this.fragment = fragment;
        this.imagesList = imagesList;
    }

    public void setImages(List<Image> images) {
        imagesList = images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item, parent, false);
        return new TimelineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        final Image image = imagesList.get(position);
        holder.description.setText(image.getDescription());
        //need to add image yeeeee
        if(image.getType().equals("CAMERA")){
            Bitmap imageBitmap = BitmapFactory.decodeFile(image.getLocation());
            holder.picture.setImageBitmap(imageBitmap);
        } else {
            Context context = holder.picture.getContext();
            Picasso.with(context).load(image.getLocation()).into(holder.picture);
        }

        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                fragment.openEditDialog(image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        Image targetImage = imagesList.get(oldPosition);

        Image tag = new Image(targetImage.getId(), targetImage.getDescription(), targetImage.getTag(), targetImage.getType(), targetImage.getLocation());
        imagesList.remove(oldPosition);
        imagesList.add(newPosition, tag);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        fragment.deleteImage(imagesList.get(position));
        imagesList.remove(position);
        notifyItemRemoved(position);
    }
}
