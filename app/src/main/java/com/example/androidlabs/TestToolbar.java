package com.example.androidlabs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

public class TestToolbar extends AppCompatActivity {

    String messege = "";
    Snackbar sb;
    EditText et;
    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        sb = Snackbar.make(tBar,"Go Back?",Snackbar.LENGTH_INDEFINITE);
        sb.setAction("Yes", e -> finish());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                if(messege=="") Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, messege, Toast.LENGTH_LONG).show();
                break;
               case R.id.item2:

                alertExample();

                break;
            case R.id.item3:
                sb.show();
                break;
            case R.id.item4:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.customdialogbox, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView image = (ImageView) middle.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.icon2);
        et = (EditText)middle.findViewById(R.id.th);
        builder.setMessage("")

                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept

                        setMessege(et.getText().toString());
                    }
                }).setView(middle)


                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }
}



