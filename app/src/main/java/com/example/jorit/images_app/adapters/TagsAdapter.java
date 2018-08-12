package com.example.jorit.images_app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.fragments.SettingsFragment;
import com.example.jorit.images_app.interfaces.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> implements ItemTouchHelperAdapter{

    private List<Tag> tagsList;
    private SettingsFragment fragment;

    public class TagsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textTagName)
        public TextView textTagName;

        public TagsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    public TagsAdapter() {
        this.tagsList = new ArrayList<>();
    }

    public TagsAdapter(SettingsFragment fragment) {
        this.fragment = fragment;
    }

    public void setTags(List<Tag> tags) {
        tagsList = tags;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);
        return new TagsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        Tag tag = tagsList.get(position);
        holder.textTagName.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {

        Tag targetTag = tagsList.get(oldPosition);

        Tag tag = new Tag(targetTag.getId(), targetTag.getName(), targetTag.isPreferred());
        tagsList.remove(oldPosition);
        tagsList.add(newPosition, tag);
        notifyItemMoved(oldPosition, newPosition);

    }

    @Override
    public void onViewSwiped(int position) {
        fragment.deleteTag(tagsList.get(position));
        tagsList.remove(position);
        notifyItemRemoved(position);
    }
}
