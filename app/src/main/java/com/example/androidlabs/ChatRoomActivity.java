package com.example.androidlabs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    ArrayList<Message> messages;
    MyOwnAdapter adapter;
    Message chatText;
    ListView mListView;
    Button buttonRecv;
    Button buttonSend;
    EditText text;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        text = (EditText) findViewById(R.id.chatType1);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonRecv = (Button) findViewById(R.id.buttonRecv);
        Log.d(TAG,"onCreate: Started.");
        mListView = (ListView) findViewById(R.id.list1);

        messages = new ArrayList<Message>();
        adapter = new MyOwnAdapter(this);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatText = new Message(text.getText().toString(), true,false);
                messages.add(chatText);
                adapter.add(chatText);
                mListView.setAdapter(adapter);
                ((EditText)findViewById(R.id.chatType1)).setText(null);
                adapter.notifyDataSetChanged();
            }
        });

        buttonRecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatText = new Message(text.getText().toString(),false,true);
                messages.add(chatText);
                adapter.add(chatText);
                mListView.setAdapter(adapter);
                ((EditText)findViewById(R.id.chatType1)).setText(null);
                adapter.notifyDataSetChanged();
            }
        });
    }
}