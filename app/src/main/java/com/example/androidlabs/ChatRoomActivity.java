package com.example.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import static com.example.androidlabs.ProfileActivity.ACTIVITY_NAME;

public class ChatRoomActivity extends Activity {
    private static final String TAG = "ChatRoomActivity";


    ListView mListView;

    Button buttonSend;
    EditText text;
    SQLiteDatabase db;
    MyDatabaseOpenHelper dbOpener;

    protected boolean isTablet;
    ArrayList<Long> chatIDs;
    ArrayList<String> chatMsgs;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        text = (EditText) findViewById(R.id.chatType1);
        buttonSend = (Button) findViewById(R.id.buttonSend);

        Log.d(TAG,"onCreate: Started.");
        mListView = (ListView) findViewById(R.id.list1);
        chatMsgs = new ArrayList<>();
        chatIDs = new ArrayList<>();

        //get a database:
         dbOpener = new MyDatabaseOpenHelper(this);
         db = dbOpener.getWritableDatabase();
        final Message messageAdapter = new Message(this);
        mListView.setAdapter(messageAdapter);
        isTablet = (findViewById(R.id.fragmentLocation) != null);
        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_TEXT_MESSAGE};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
       //find the column indices:
        int text_message_ColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_TEXT_MESSAGE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        while(results.moveToNext()) {
           chatMsgs.add(results.getString(text_message_ColIndex));
           chatIDs.add(results.getLong(idColIndex));
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle newBundle = new Bundle();
                newBundle.putString("message", messageAdapter.getItem(position));
                newBundle.putLong("id", messageAdapter.getId(position));
                // Action if tablet
                if (isTablet)
                {
                    MessageFragment messageFragment = new MessageFragment(ChatRoomActivity.this);
                    messageFragment.setArguments(newBundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentLocation, messageFragment);
                    ft.commit();
                }
                else
                {
                    Intent msgDetailsIntent = new Intent(getApplicationContext(), MessageDetails.class);
                    msgDetailsIntent.putExtras(newBundle);
                    startActivityForResult(msgDetailsIntent,CONTEXT_INCLUDE_CODE);
                }
            }
        });
            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!text.getText().toString().trim().equals("")) {

                        chatMsgs.add(text.getText().toString());
                        ContentValues newRowValues = new ContentValues();
                        newRowValues.put(MyDatabaseOpenHelper.COL_TEXT_MESSAGE, text.getText().toString());
                        chatIDs.add(db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues));
                        messageAdapter.notifyDataSetChanged();
                        ((EditText) findViewById(R.id.chatType1)).setText(null);
                    }
                }
            });
    }

    public class Message extends ArrayAdapter<String>{
        private String messageText;
        private boolean sent;
        private boolean received;
        long id;

        public Message(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return chatMsgs.size();
        }

        public String getItem(int position)
        {
            return chatMsgs.get(position);
        }

        public long getId(int position)
        {
            return chatIDs.get(position);
        }

        public void setId(long id) {
            this.id = id;
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater = ChatRoomActivity.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
            {
                result = inflater.inflate(R.layout.left_row, null);
            }
            else
            {
                result = inflater.inflate(R.layout.right_row, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (resultCode == CONTEXT_INCLUDE_CODE)
        {
            Long msgID = data.getLongExtra("msgID",-1);
            Log.i(ACTIVITY_NAME, "Request Deletion of message with id: " + Long.toString(msgID));
            deleteItem(msgID);
        }
    }

    public void deleteItem(long id)
    {
        db.delete(MyDatabaseOpenHelper.TABLE_NAME, MyDatabaseOpenHelper.COL_ID + "=" + id, null);

        int position = chatIDs.indexOf(id);
        chatMsgs.remove(position);
        chatIDs.remove(position);

        final Message adapter = (Message) mListView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}


