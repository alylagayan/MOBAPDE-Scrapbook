package com.example.alysa.mobapde_scrapbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Access_Module extends AppCompatActivity {

    private Button btn_Login, btn_Register;
    private EditText username_input, pword_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access__module);

        username_input = (EditText) findViewById(R.id.username_input);
        username_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pword_textView = (EditText) findViewById(R.id.pword_textView);
        pword_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchApp();
            }
        });

        btn_Register = (Button) findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerInApp();
            }
        });


    }

    private void launchApp() {
        Intent intent = new Intent(this, App_Module.class);
        startActivity(intent);
    }

    private void registerInApp() {

        Intent intent = new Intent(this, Register_Module.class);
        startActivity(intent);
    }


}
