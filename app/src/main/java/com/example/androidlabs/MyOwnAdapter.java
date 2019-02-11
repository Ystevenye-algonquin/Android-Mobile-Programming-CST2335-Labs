package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MyOwnAdapter extends BaseAdapter
{   TextView messageBody;
    List<Message> messagesText = new ArrayList<Message>();
    Context context;

    public MyOwnAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messagesText.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messagesText.size();
    }
    @Override
    public Message getItem(int position){
        return messagesText.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return messagesText.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messagesText.get(position);

        if (message.isSent()) {
            convertView = messageInflater.inflate(R.layout.right_row, null);
            messageBody = (TextView) convertView.findViewById(R.id.right_row_text);
            convertView.setTag(messageBody);
            messageBody.setText(message.getMessageText());
        } else {
            convertView = messageInflater.inflate(R.layout.left_row, null);
            messageBody = (TextView) convertView.findViewById(R.id.left_row_text);
            convertView.setTag(messageBody);
            messageBody.setText(message.getMessageText());
        }

        return convertView;

    }
}