package com.example.travelbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessagesAdapter extends ArrayAdapter<Message> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;

    public MessagesAdapter(Context context, List<Message> data) {
        super(context, R.layout.item_user_msg, data);
    }

    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        Message item = getItem(position);

        if (item.isMine()) return MY_MESSAGE;
        else  return OTHER_MESSAGE;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user_msg, parent, false);
            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());

        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bot_msg, parent, false);
            TextView textView = convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }

        return convertView;
    }
}
