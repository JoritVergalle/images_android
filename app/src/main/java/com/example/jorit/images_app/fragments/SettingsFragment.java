package com.example.jorit.images_app.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.adapters.SpinnerAdapter;
import com.example.jorit.images_app.adapters.TagsAdapter;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.helpers.tagTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    //private List<Tag> tagList = new ArrayList<>();
    private Box<Tag> tagsBox;
    private Query<Tag> tagsQuery;

    @BindView(R.id.spinnerTags)
    Spinner spinner;

    @BindView(R.id.buttonAddTag)
    Button buttonAddTag;

    @BindView(R.id.editTextNewTag)
    EditText editTextNewTag;

    @BindView(R.id.tags_recycler_view)
    RecyclerView tagsRecyclerView;

    private TagsAdapter tagsAdapter;
    private SpinnerAdapter spinnerAdapter;

    private ItemTouchHelper tagsTouchHelper;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);

//        Tag tag = new Tag("Duidt de tags aan die u wilt zien.", true);
//        tagList.add(tag);
//        tag = new Tag("Lifestyle", true);
//        tagList.add(tag);
//        tag = new Tag("Food", false);
//        tagList.add(tag);
//        tag = new Tag("Hobby", false);
//        tagList.add(tag);
//        tag = new Tag("Fun", true);
//        tagList.add(tag);
//        tag = new Tag("Gaming", true);
//        tagList.add(tag);

        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        tagsBox = boxStore.boxFor(Tag.class);
        tagsQuery = tagsBox.query().build();


        List<Tag> tagsList = tagsQuery.find();

        spinnerAdapter = new SpinnerAdapter(getActivity(), 0,
                tagsList, SettingsFragment.this);
        spinner.setAdapter(spinnerAdapter);

        tagsAdapter = new TagsAdapter(SettingsFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        tagsRecyclerView.setLayoutManager(mLayoutManager);
        //timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tagsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        tagsRecyclerView.setAdapter(tagsAdapter);

        ItemTouchHelper.Callback callback = new tagTouchHelperCallback(tagsAdapter);
        tagsTouchHelper = new ItemTouchHelper(callback);
        tagsTouchHelper.attachToRecyclerView(tagsRecyclerView);

        updateTags();

        return v;
    }

    private void updateTags() {
        List<Tag> tags = tagsQuery.find();
        tagsAdapter.setTags(tags);
        spinnerAdapter = new SpinnerAdapter(getActivity(), 0,
                tags, SettingsFragment.this);
        spinner.setAdapter(spinnerAdapter);
    }

    @OnClick(R.id.buttonAddTag)
    public void addTag()  {
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            if(!editTextNewTag.getText().toString().equals("")){
                Tag tag = new Tag();
                tag.setName(editTextNewTag.getText().toString());
                tag.setPreferred(false);
                tagsBox.put(tag);
                //tagList.add(tag);
                updateTags();
                editTextNewTag.setText("");
            }
        } catch (Exception e) {

        }

    }

    public void deleteTag(Tag tag){
        tagsBox.remove(tag);
        updateTags();
    }

    public void updateTag(Tag tag){
        tagsBox.put(tag);

    }
}
