package com.example.jorit.images_app.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Flickr.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.FlickrViewHolder> {

    private List<Photo> photoList;

    public class FlickrViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flickrImage)
        public ImageView flickrImage;

        public FlickrViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    public FlickrAdapter(List<Photo> photoList) {
        this.photoList = photoList;
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
        Photo photo = photoList.get(position);
        String uri = "https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg";
        Log.d("Hallo", uri);
        //TODO SET PHOTO
//        Uri uri = Uri.parse("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg");
//        holder.flickrImage.setImageURI(uri);
        Context context = holder.flickrImage.getContext();

        Picasso.with(context).load("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg").into(holder.flickrImage);
        //Picasso.get().load("https://farm" + photo.getFarm() + ".staticflickr.com/"+ photo.getServer() +"/"+ photo.getId() +"_"+ photo.getSecret() +"_q.jpg").into(holder.flickrImage);
    }
    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setFlickrPhotos(List<Photo> flickrPhotos) {
        this.photoList = flickrPhotos;
    }

}
