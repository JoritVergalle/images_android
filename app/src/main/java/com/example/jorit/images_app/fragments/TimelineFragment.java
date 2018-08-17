package com.example.jorit.images_app.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.adapters.TimelineAdapter;
import com.example.jorit.images_app.domain.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    private List<Image> imagesList = new ArrayList<>();

    @BindView(R.id.timeline_recycler_view)
    RecyclerView timelineRecyclerView;

    @BindView(R.id.fabAddImageFragment)
    FloatingActionButton fabAddImageFragment;

    private TimelineAdapter timelineAdapter;


    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, v);

        timelineAdapter = new TimelineAdapter(imagesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(mLayoutManager);
        //timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timelineRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        timelineRecyclerView.setAdapter(timelineAdapter);

        Image image = new Image(1242421, "Toen we s'middags naar het stal gingen waren we zeer verbaast. We zagen dat één van kalfjes uitgebroken was uit de weide en tot bij onze ruin geraakte. Gelukkig namen we deze foto snel want de pret was de volgende minuut voorbij ;)", "Lifestyle", "ha", "ha");
        imagesList.add(image);
        imagesList.add(image);
        imagesList.add(image);
        imagesList.add(image);
        imagesList.add(image);
        imagesList.add(image);
        imagesList.add(image);

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
}
