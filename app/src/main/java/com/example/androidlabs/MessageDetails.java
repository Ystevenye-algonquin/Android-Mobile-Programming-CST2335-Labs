package com.example.androidlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.app.FragmentTransaction;

public class MessageDetails extends AppCompatActivity {
    protected FrameLayout fl;
    protected MessageFragment messageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fl = new FrameLayout(this);
        fl.setId(R.id.fragmentLocation);
        setContentView(fl);

        messageFragment= new MessageFragment();
        messageFragment.setArguments(getIntent().getExtras());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLocation, messageFragment);
        ft.commit();
    }
}
