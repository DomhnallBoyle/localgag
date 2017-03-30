package com.example.domhnallboyle.localgag.Screens.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Engine.Objects.Message;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Fragments.MailFragment;
import com.example.domhnallboyle.localgag.Screens.Fragments.ProfileFragment;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Toolbar toolbar = (Toolbar) findViewById(R.id.messaging_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back = (Button)toolbar.findViewById(R.id.back_from_messaging);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new Functions(getApplicationContext(), getSupportFragmentManager(), FunctionType.GET_FRIENDS, null).execute();
    }
}
