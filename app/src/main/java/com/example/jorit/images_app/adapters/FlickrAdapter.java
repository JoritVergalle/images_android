package com.example.jorit.images_app.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Flickr.Photo;
import com.example.jorit.images_app.fragments.AddImageFragment;
import com.example.jorit.images_app.interfaces.ItemLongClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.FlickrViewHolder> {

    private List<Photo> photoList;
    private AddImageFragment addImageFragment;

    public class FlickrViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        @BindView(R.id.flickrImage)
        public ImageView flickrImage;

        ItemLongClickListener itemLongClickListener;

        public FlickrViewHolder(View view) {
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

    public FlickrAdapter(List<Photo> photoList, AddImageFragment addImageFragment) {
        this.photoList = photoList;
        this.addImageFragment = addImageFragment;
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flickr_item, parent, false);
        Log.d("monka", "MonkaS very hard");
        return new FlickrViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(FlickrViewHolder holder, int position) {
        final Photo photo = photoList.get(position);

        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                addImageFragment.setPreviewFlickr("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg");
                Log.d("LongCLIIIIIIIIIIK", "https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg");
            }
        });
        //TODO SET PHOTO
//        Uri uri = Uri.parse("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg");
//        holder.flickrImage.setImageURI(uri);
        Context context = holder.flickrImage.getContext();

        Picasso.with(context).load("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg").into(holder.flickrImage);

    }
    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setFlickrPhotos(List<Photo> flickrPhotos) {
        this.photoList = flickrPhotos;
    }

}
