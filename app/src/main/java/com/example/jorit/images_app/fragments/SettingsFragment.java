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

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private List<Tag> tagList = new ArrayList<>();

    @BindView(R.id.spinnerTags)
    Spinner spinner;

    @BindView(R.id.buttonAddTag)
    Button buttonAddTag;

    @BindView(R.id.editTextNewTag)
    EditText editTextNewTag;

    @BindView(R.id.tags_recycler_view)
    RecyclerView tagsRecyclerView;

    private TagsAdapter tagsAdapter;

    private ItemTouchHelper tagsTouchHelper;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onceateSettingsFragment", "FRAGMENT IS AANGEMAAKT");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);

        Tag tag = new Tag("Duidt de tags aan die u wilt zien.", true);
        tagList.add(tag);
        tag = new Tag("Lifestyle", true);
        tagList.add(tag);
        tag = new Tag("Food", false);
        tagList.add(tag);
        tag = new Tag("Hobby", false);
        tagList.add(tag);
        tag = new Tag("Fun", true);
        tagList.add(tag);
        tag = new Tag("Gaming", true);
        tagList.add(tag);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), 0,
                tagList);
        spinner.setAdapter(spinnerAdapter);

        tagsAdapter = new TagsAdapter(tagList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        tagsRecyclerView.setLayoutManager(mLayoutManager);
        //timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tagsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        tagsRecyclerView.setAdapter(tagsAdapter);

        ItemTouchHelper.Callback callback = new tagTouchHelperCallback(tagsAdapter);
        tagsTouchHelper = new ItemTouchHelper(callback);
        tagsTouchHelper.attachToRecyclerView(tagsRecyclerView);


        tagsAdapter.notifyDataSetChanged();

        return v;
    }

    @OnClick(R.id.buttonAddTag)
    public void addTag()  {
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            if(!editTextNewTag.getText().toString().equals("")){
                Tag tag = new Tag(editTextNewTag.getText().toString(), false);
                tagList.add(tag);
                editTextNewTag.setText("");
            }
        } catch (Exception e) {

        }

    }

}
