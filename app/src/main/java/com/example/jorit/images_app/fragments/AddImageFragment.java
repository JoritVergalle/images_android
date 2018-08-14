package com.example.jorit.images_app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageFragment extends Fragment {
    private Box<Tag> tagsBox;
    private Query<Tag> tagsQuery;

    @BindView(R.id.spinnerTagNewImage)
    Spinner spinner;

    @BindView(R.id.editTextNewTag)
    EditText description;

    public AddImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_image, container, false);
        ButterKnife.bind(this, v);

        //MyList.Select(x=>x.Name).ToArray();

        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
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

        return v;
    }

}
