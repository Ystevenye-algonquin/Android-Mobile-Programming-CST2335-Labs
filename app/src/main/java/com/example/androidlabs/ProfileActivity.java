package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageButton imageButton2;
    TextView emailInput2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_linear);
//        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_main_relative);
//        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_profile_activity);

        Log.e(ACTIVITY_NAME, "In function:"+"onCreate Function");

        emailInput2 = (EditText) findViewById(R.id.emailInput2);
        final Intent intent = getIntent();
        String value = intent.getStringExtra("emailAddress");
        emailInput2.setText(value);


//        emailInput2 = (TextView) findViewById(R.id.emailInput2);
//        SharedPreferences shardEmail = getSharedPreferences("savedEmail",Context.MODE_PRIVATE);
//        String value = shardEmail.getString("emailInput1", emailInput2.getText().toString());
//        emailInput2.setText(value);
//


        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(btn ->
                dispatchTakePictureIntent()
                );
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:"+"onStart Function");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:"+"onStop Function");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:"+"onDestroy Function");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:"+"onPause Function");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:"+"onResume Function");
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton2.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function:"+"onActivityResult Function");
    }




}
