package com.example.jorit.images_app.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Image;
import com.example.jorit.images_app.domain.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class EditImageDialogFragment extends DialogFragment {

    private Box<Tag> tagsBox;
    private Query<Tag> tagsQuery;

    private Box<Image> imageBox;
    private Query<Image> imageQuery;

    @BindView(R.id.spinnerDialog)
    Spinner spinnerDialog;

    @BindView(R.id.descriptionDialog)
    EditText descriptionDialog;

    private Image image;

    public EditImageDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            image = new Image(bundle.getLong("id"),bundle.getString("description"), bundle.getString("tag"), bundle.getString("type"), bundle.getString("location"));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.edit_image_dialog, null);
        ButterKnife.bind(this, view);
        descriptionDialog.setText(image.getDescription());




        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        imageBox = boxStore.boxFor(Image.class);
        imageQuery = imageBox.query().build();
        tagsBox = boxStore.boxFor(Tag.class);
        tagsQuery = tagsBox.query().build();

        List<Tag> tagsList = tagsQuery.find();
        List<String> tags = new ArrayList<>();

        for(Tag tag : tagsList) {
            tags.add(tag.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tags);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialog.setAdapter(dataAdapter);


        builder.setTitle("Edit image")
                .setView(view)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!image.getDescription().equals(descriptionDialog.getText().toString()) || !image.getTag().equals(spinnerDialog.getSelectedItem().toString())) {
                            image.setDescription(descriptionDialog.getText().toString());
                            image.setTag(spinnerDialog.getSelectedItem().toString());
                            imageBox.put(image);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        //LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
//                // Add action buttons
//                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // sign in the user ...
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
//                    }
//                });
//        return builder.create();
    }
}
