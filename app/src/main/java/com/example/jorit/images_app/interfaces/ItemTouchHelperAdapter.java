package com.example.jorit.images_app.interfaces;

public interface ItemTouchHelperAdapter {
    void onViewMoved(int oldPosition, int newPosition);

    void onViewSwiped(int position);
}
