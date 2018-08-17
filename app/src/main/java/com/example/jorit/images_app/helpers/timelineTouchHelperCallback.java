package com.example.jorit.images_app.helpers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.jorit.images_app.adapters.TimelineAdapter;

public class timelineTouchHelperCallback extends ItemTouchHelper.Callback {

    private TimelineAdapter timelineAdapter;

    public timelineTouchHelperCallback(TimelineAdapter timelineAdapter) {
        this.timelineAdapter = timelineAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        timelineAdapter.onViewMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        timelineAdapter.onViewSwiped(viewHolder.getAdapterPosition());
    }
}
