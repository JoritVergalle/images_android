package com.example.jorit.images_app.interfaces;

//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf
public interface ItemTouchHelperAdapter {
    void onViewMoved(int oldPosition, int newPosition);

    void onViewSwiped(int position);
}
