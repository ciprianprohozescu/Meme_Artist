package com.cyworks.memeartist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


class MemeAdapter extends ArrayAdapter<String> {

    public MemeAdapter(Context context, String[] things) {
        super(context, R.layout.meme_row, things);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.meme_row, parent, false);
        String singleThing = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.memeListText);
        myText.setText(singleThing);
        return customView;
    }
}
