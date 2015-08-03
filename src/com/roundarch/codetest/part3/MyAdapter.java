package com.roundarch.codetest.part3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roundarch.codetest.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<MyLocation> {
    public MyAdapter(Context context, ArrayList<MyLocation> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyLocation user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.part3_listview_item,
                    parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textView);
        tvName.setText("Lat.: " + user.getLatitude()
                + ", Lon.: "
                + user.getLongitude()
                + ", ZipCode.: :"
                + user.getZipcode());

        return convertView;
    }
}
