package com.example.androidlabs;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import java.util.ArrayList;
import java.util.List;

import static com.example.androidlabs.ProfileActivity.ACTIVITY_NAME;

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    List<Message> messages;
    MyOwnAdapter adapter;
    Message chatText;
    ListView mListView;
    Button buttonRecv;
    Button buttonSend;
    EditText text;
    SQLiteDatabase db;
    MyDatabaseOpenHelper dbOpener;
    SimpleCursorAdapter simplecursor;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        text = (EditText) findViewById(R.id.chatType1);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonRecv = (Button) findViewById(R.id.buttonRecv);
        Log.d(TAG,"onCreate: Started.");
        mListView = (ListView) findViewById(R.id.list1);


        //get a database:
         dbOpener = new MyDatabaseOpenHelper(this);
         db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_SENT,MyDatabaseOpenHelper.COL_RECEIVED, MyDatabaseOpenHelper.COL_TEXT_MESSAGE};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);


//**************************************************************
        int[] to = new int[]{ R.id.left_row_text, R.id.right_row_text };
        simplecursor = new SimpleCursorAdapter (this, android.R.layout.simple_list_item_1, results, columns, to, 0);
        mListView.setAdapter(simplecursor);
//**************************************************************


        //find the column indices:
        int sent_ColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_SENT);
        int received_ColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_RECEIVED);
        int text_message_ColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_TEXT_MESSAGE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()) {
            String sent_col = results.getString(sent_ColumnIndex);
            String received_col = results.getString(received_ColumnIndex);
            String text_message_col = results.getString(text_message_ColIndex);
            long id_col = results.getLong(idColIndex);


            //add the new Contact to the array list:
            messages = new ArrayList<Message>();
            if (sent_col.equals("Sent")) {
                messages.add(new Message(text_message_col, true, false, id_col));
            }
            if (received_col.equals("Received")) {
                messages.add(new Message(text_message_col, false, true, id_col));
            }

        }
            adapter = new MyOwnAdapter(this);

            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the textMessages that were typed
                    String textMessages = text.getText().toString();
                    String sent = "Sent";
                    String received = "";
                    //add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    //put string text_message in the COL_TEXT_MESSAGE column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_TEXT_MESSAGE, textMessages);
                    //put string sent in the COL_SENT column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_SENT, sent);
                    //put string received in the COL_RECEIVED column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_RECEIVED, received);
                    //insert in the database:
                    long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
                    chatText = new Message(textMessages, true, false, newId);
                    adapter.add(chatText);
                    mListView.setAdapter(adapter);
                    ((EditText) findViewById(R.id.chatType1)).setText(null);
                    adapter.notifyDataSetChanged();
                }


            });


            buttonRecv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the textMessages that were typed
                    String textMessages = text.getText().toString();
                    String sent = "";
                    String received = "Received";
                    //add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    //put string text_message in the COL_TEXT_MESSAGE column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_TEXT_MESSAGE, textMessages);
                    //put string received in the COL_RECEIVE column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_RECEIVED, received);
                    //put string sent in the COL_SENT column:
                    newRowValues.put(MyDatabaseOpenHelper.COL_SENT, sent);
                    //insert in the database:
                    long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
                    chatText = new Message(textMessages, false, true, newId);
                    adapter.add(chatText);
                    mListView.setAdapter(adapter);
                    ((EditText) findViewById(R.id.chatType1)).setText(null);
                    adapter.notifyDataSetChanged();
                }


            });

            Cursor c = db.rawQuery("SELECT * from " + MyDatabaseOpenHelper.TABLE_NAME, null);
//             if(!c.isBeforeFirst())
             printCursor(c);



    }


//    private static int ACTIVITY_PROFILE_ACTIVITY = 33;
//    private static int ACTIVITY_CHAT_ROOM=34;
//
//   @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//         super.startActivityForResult(intent, requestCode);
//        if(requestCode==34)
//            mListView.setAdapter(simplecursor);
//        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult Function");
//    }


   public void printCursor( Cursor c) {

       int colIndex0 = c.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
       int colIndex1 = c.getColumnIndex(MyDatabaseOpenHelper.COL_SENT);
       int colIndex2 = c.getColumnIndex(MyDatabaseOpenHelper.COL_RECEIVED);
       int colIndex3 = c.getColumnIndex(MyDatabaseOpenHelper.COL_TEXT_MESSAGE);
       Log.e("******Database Version:", String.valueOf(MyDatabaseOpenHelper.VERSION_NUM));
       c.moveToFirst();
       c.moveToFirst();
       Log.e("******Number of columns", String.valueOf(c.getColumnCount()));
       c.moveToFirst();
       for (int i = 0; i < c.getColumnCount(); i++) {
           Log.e("******Name of column " + (i + 1) + " is", c.getColumnNames()[i]);
           c.moveToNext();
       }
       c.moveToFirst();
       Log.e("******Number of results", String.valueOf(c.getCount()));
       c.moveToFirst();
       String n_word = new String(" SENT: ");
       String e_word = new String (" RECEIVED: ");
       for (int i = 0; i < c.getCount(); i++) {
           String id = c.getString(colIndex0);
           String n = c.getString(colIndex1);
           String e = c.getString(colIndex2);
           String r = c.getString(colIndex3);
           if (n.equals("Sent"))
           Log.e("******Result " + (i + 1) + " is", "ID "+id + n_word + r);
           if (e.equals("Received"))
               Log.e("******Result " + (i + 1) + " is", "ID "+id + e_word + r);
           c.moveToNext();
       }

   }


}