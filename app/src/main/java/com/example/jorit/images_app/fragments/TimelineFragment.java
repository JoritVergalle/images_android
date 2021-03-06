package com.example.jorit.images_app.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorit.images_app.App;
import com.example.jorit.images_app.R;
import com.example.jorit.images_app.adapters.TimelineAdapter;
import com.example.jorit.images_app.domain.Image;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.helpers.timelineTouchHelperCallback;

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
public class TimelineFragment extends Fragment implements EditImageDialogFragment.EditImageDialogListener{

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
        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Timeline");

        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        imagesBox = boxStore.boxFor(Image.class);
        imagesQuery = imagesBox.query().build();

        Box<Tag> tagsBox = boxStore.boxFor(Tag.class);
        Query<Tag> tagsQuery = tagsBox.query().build();

        List<Tag> tagsList = tagsQuery.find();
        if(tagsList.isEmpty()){
            Tag tag = new Tag();
            tag.setName("Relationships");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Living");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Spare time");
            tag.setPreferred(true);
            tagsBox.put(tag);
            tag = new Tag();
            tag.setName("Health");
            tag.setPreferred(true);
            tagsBox.put(tag);
        }

        List<Image> imagesList = imagesQuery.find();
        List<Image> imagesFiltered = new ArrayList<>();
        tagsList = tagsQuery.find();
        for(Tag tag : tagsList){
            if(tag.isPreferred()){
                for(Image image : imagesList){
                    if(image.getTag().equals(tag.getName())) {
                        imagesFiltered.add(image);
                    }
                }
            }
        }

        //timelineAdapter = new TimelineAdapter(imagesList);
        timelineAdapter = new TimelineAdapter(TimelineFragment.this, imagesFiltered);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(mLayoutManager);
        //timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timelineRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        timelineRecyclerView.setAdapter(timelineAdapter);

        ItemTouchHelper.Callback callback = new timelineTouchHelperCallback(timelineAdapter);
        timelineTouchHelper = new ItemTouchHelper(callback);
        timelineTouchHelper.attachToRecyclerView(timelineRecyclerView);

        timelineAdapter.notifyDataSetChanged();

        return v;
    }

    @OnClick(R.id.fabAddImageFragment)
    public void ClickfabAddImageFragment() {
        Fragment fragment = new AddImageFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void deleteImage(Image image){
        imagesBox.remove(image);
    }


    public void openEditDialog(Image image) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        DialogFragment dialogFragment = new EditImageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", image.getId());
        bundle.putString("description", image.getDescription());
        bundle.putString("tag", image.getTag());
        bundle.putString("type", image.getType());
        bundle.putString("location", image.getLocation());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(transaction, "dialog");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
       Log.d("LISTENER", " WERKT SPIJTIG GENOEG NIET :/");
    }
}
