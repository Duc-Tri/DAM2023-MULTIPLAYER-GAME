package com.mygdx.bagarre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<ImageItem> {
    private Context context;
    private List<ImageItem> imageList;

    public ImageAdapter(Context context, int resource, List<ImageItem> imageList) {
        super(context, resource, imageList);
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageList.get(position).getImageId());
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return imageView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        ImageView imageView = row.findViewById(R.id.spinner_image);
        imageView.setImageResource(imageList.get(position).getImageId());

        return row;
    }
}
