package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidlabs.Message;
import com.example.androidlabs.MyDatabaseOpenHelper;
import com.example.androidlabs.R;

import java.util.ArrayList;
import java.util.List;

public class MyCursorAdapter extends CursorAdapter {
    Message messageTextsBack;
    Cursor cursor;
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    Context context;
    List<Message> messagesText = new ArrayList<Message>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater1 = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messagesText.get(position);

        if (message.isSent()) {
            convertView = messageInflater1.inflate(R.layout.right_row, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.right_row_text);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessageText());
        } else {
            convertView = messageInflater1.inflate(R.layout.left_row, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.left_row_text);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessageText());
        }

        return convertView;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }


    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.



    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView left = (TextView) view.findViewById(R.id.left_row_text);
        TextView right = (TextView) view.findViewById(R.id.right_row_text);
        // Extract properties from cursor
        int sent_ColumnIndex = cursor.getColumnIndex(MyDatabaseOpenHelper.COL_SENT);
        int received_ColumnIndex = cursor.getColumnIndex(MyDatabaseOpenHelper.COL_RECEIVED);
        int text_message_ColIndex = cursor.getColumnIndex(MyDatabaseOpenHelper.COL_TEXT_MESSAGE);
        int idColIndex = cursor.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        String sent_col = cursor.getString(sent_ColumnIndex);
        String received_col = cursor.getString(received_ColumnIndex);
        String text_message_col = cursor.getString(text_message_ColIndex);
        long id_col = cursor.getLong(idColIndex);
        // Populate fields with extracted properties
        left.setText(sent_col);
        right.setText(received_col);

    }


}