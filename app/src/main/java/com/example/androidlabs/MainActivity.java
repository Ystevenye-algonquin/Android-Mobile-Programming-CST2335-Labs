package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText emailInput1;
    Button loginButton;
    SharedPreferences sp;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_linear);
//        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_main_relative);
        setContentView(R.layout.activity_login);

        emailInput1 = (EditText) findViewById(R.id.emailInput1);
        loginButton = (Button) findViewById(R.id.button2);

        sp = getSharedPreferences("emailAddress", Context.MODE_PRIVATE);

        String emailText = sp.getString("emailAddress", "");
        emailInput1.setText(emailText);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(MainActivity.this, ProfileActivity.class);
//                intent.putExtra("emailAddress", emailInput1.getText().toString());
//                startActivity(intent);
//            }
//        });

        loginButton.setOnClickListener(e->{
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("emailAddress",emailInput1.getText().toString());
            startActivity(intent);

        });
    }

    protected void onPause () {
        super.onPause();
        SharedPreferences.Editor edit = sp.edit();
        intent.putExtra("emailAddress", emailInput1.getText().toString());
        edit.putString("emailAddress", emailInput1.getText().toString());
        edit.commit();
    }


}

