package com.example.jorit.images_app.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.adapters.FlickrAdapter;
import com.example.jorit.images_app.domain.Flickr.BaseResponse;
import com.example.jorit.images_app.domain.Flickr.Photo;
import com.example.jorit.images_app.domain.Image;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.network.GetDataService;
import com.example.jorit.images_app.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageFragment extends Fragment {
    private Box<Tag> tagsBox;
    private Query<Tag> tagsQuery;

    private Box<Image> imageBox;
    private Query<Image> imageQuery;

    private List<Photo> photoList = new ArrayList<>();
    private FlickrAdapter flickrAdapter;

    String mCurrentPhotoPath = "";
    String fotoType = "";

    @BindView(R.id.spinnerTagNewImage)
    Spinner spinner;

    @BindView(R.id.editTextNewTag)
    EditText description;

    @BindView(R.id.addImagePreview)
    ImageView addImagePreview;

    @BindView(R.id.buttonAddImage)
    Button buttonAddImage;

    @BindView(R.id.flickr_recycler_view)
    RecyclerView flickr_recycler_view;

    public AddImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_image, container, false);
        ButterKnife.bind(this, v);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Add new image");

        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        imageBox = boxStore.boxFor(Image.class);
        imageQuery = imageBox.query().build();

        //MyList.Select(x=>x.Name).ToArray();

        //BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        tagsBox = boxStore.boxFor(Tag.class);
        tagsQuery = tagsBox.query().build();
        List<Tag> tagsList = tagsQuery.find();
        List<String> tags = new ArrayList<>();

        for(Tag tag : tagsList) {
            tags.add(tag.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tags);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        flickrAdapter = new FlickrAdapter(photoList, AddImageFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        flickr_recycler_view.setLayoutManager(mLayoutManager);
        flickr_recycler_view.setAdapter(flickrAdapter);

        flickrAdapter.notifyDataSetChanged();

        flickr_recycler_view.setAlpha(0);

        return v;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        fotoType = "CAMERA";
        return image;
    }

    @OnClick(R.id.FabTakePicture)
    public void TakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.jorit.images_app.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @OnClick(R.id.buttonAddImage)
    public void addImage() {
        if(!mCurrentPhotoPath.equals("") && !fotoType.equals("") && !description.getText().toString().equals("")) {
            Image image = new Image(0, description.getText().toString(), spinner.getSelectedItem().toString(), fotoType, mCurrentPhotoPath);
            imageBox.put(image);
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        } else {
            Toast.makeText(getActivity(), "Please choose an image and fill in a description", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.buttonSearchFlickr)
    public void searchFlickr() {

        GetDataService caller = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<BaseResponse> call = caller.getFlickrImages("flickr.photos.search","a1f79d9845c842d2151c1a14a3f5fb01", spinner.getSelectedItem().toString(), "json", "1");

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("heee","BackendCall search (tag) to flickr, response: " + response.body());
                if (response.isSuccessful()) {
                    Log.d("heee", "response body from call: " + response.body().toString());
                    BaseResponse jsonRepsonse = response.body();
                    Log.d("Line 164:", response.body().getStat());

                    List<Photo> photoList = new ArrayList<>(jsonRepsonse.getPhotos().getPhoto());

                    flickrAdapter.setFlickrPhotos(photoList);

                    flickrAdapter.notifyDataSetChanged();
                    flickr_recycler_view.setAlpha(1);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Tag", "search tag backendCall failed, call " + call.request().toString() + "t =" + t.getMessage().toString());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Log.d("picturePath", mCurrentPhotoPath);
            addImagePreview.setImageBitmap(imageBitmap);
        }
    }

    public void setPreviewFlickr(String previewFlickr) {
        Context context = addImagePreview.getContext();
        Picasso.with(context).load(previewFlickr).into(addImagePreview);
        fotoType = "FLICKR";
        mCurrentPhotoPath = previewFlickr;

    }
}
