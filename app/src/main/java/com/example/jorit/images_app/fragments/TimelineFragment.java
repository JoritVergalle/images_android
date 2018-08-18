package com.example.jorit.images_app.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.adapters.TimelineAdapter;
import com.example.jorit.images_app.domain.Image;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.helpers.timelineTouchHelperCallback;

import java.sql.Time;
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
public class TimelineFragment extends Fragment {

    //private List<Image> imagesList = new ArrayList<>();
    private Box<Image> imagesBox;
    private Query<Image> imagesQuery;

    @BindView(R.id.timeline_recycler_view)
    RecyclerView timelineRecyclerView;

    @BindView(R.id.fabAddImageFragment)
    FloatingActionButton fabAddImageFragment;

    private TimelineAdapter timelineAdapter;

    private ItemTouchHelper timelineTouchHelper;

    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, v);

        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        imagesBox = boxStore.boxFor(Image.class);
        imagesQuery = imagesBox.query().build();

        Box<Tag> tagsBox = boxStore.boxFor(Tag.class);
        Query<Tag> tagsQuery = tagsBox.query().build();

        List<Tag> tagsList = tagsQuery.find();
        if(tagsList.isEmpty()){
            Tag tag = new Tag();
            tag.setName("Relaties");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Wonen");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Vrije tijd & dagbesteding");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Gezondheid & Welzijn");
            tag.setPreferred(true);
            tagsBox.put(tag);
        }


        List<Image> imagesList = imagesQuery.find();

        //timelineAdapter = new TimelineAdapter(imagesList);
        timelineAdapter = new TimelineAdapter(TimelineFragment.this, imagesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(mLayoutManager);
        //timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timelineRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        timelineRecyclerView.setAdapter(timelineAdapter);

//        Image image = new Image(1242421, "Toen we s'middags naar het stal gingen waren we zeer verbaast. We zagen dat één van kalfjes uitgebroken was uit de weide en tot bij onze ruin geraakte. Gelukkig namen we deze foto snel want de pret was de volgende minuut voorbij ;)", "Lifestyle", "ha", "ha");
//        imagesList.add(image);
//        imagesList.add(image);
//        imagesList.add(image);
//        imagesList.add(image);
//        imagesList.add(image);
//        imagesList.add(image);
//        imagesList.add(image);

        ItemTouchHelper.Callback callback = new timelineTouchHelperCallback(timelineAdapter);
        timelineTouchHelper = new ItemTouchHelper(callback);
        timelineTouchHelper.attachToRecyclerView(timelineRecyclerView);

        timelineAdapter.notifyDataSetChanged();

        return v;
    }

    @OnClick(R.id.fabAddImageFragment)
    public void ClickfabAddImageFragment() {
        Fragment fragment = null;

            fragment = new AddImageFragment();
            //getActivity().getSupportActionBar.setTitle("Settings");


        // Replacing the fragment.
        if (fragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.timelineFragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public void deleteImage(Image image){
        imagesBox.remove(image);
    }
}
