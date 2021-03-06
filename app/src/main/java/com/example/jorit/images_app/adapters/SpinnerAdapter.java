package com.example.jorit.images_app.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jorit.images_app.R;
import com.example.jorit.images_app.domain.Tag;
import com.example.jorit.images_app.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Tag> {
    private Context mContext;
    private List<Tag> listState;
    private SpinnerAdapter spinnerAdapter;
    private SettingsFragment fragment;
    private boolean isFromView = false;

    public SpinnerAdapter(Context context, int resource, List<Tag> objects, SettingsFragment  fragment) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = objects;
        this.spinnerAdapter = this;
        this.fragment = fragment;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.textTagNameSpinner);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkboxPreferredSpinner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getName());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isPreferred());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setPreferred(isChecked);
                    fragment.updateTag(listState.get(position));
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    public void setTags(List<Tag> tags) {
        listState = tags;
        notifyDataSetChanged();
    }
}
