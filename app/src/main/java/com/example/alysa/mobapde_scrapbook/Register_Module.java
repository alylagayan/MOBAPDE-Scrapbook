package com.example.alysa.mobapde_scrapbook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alysa on 12/3/2017.
 */

public class Register_Module extends Activity {

    private DatabaseHelper db;

    private static final String TAG = Register_Module.class.getSimpleName();
    Button btn_regToAccess, btn_Register;
    EditText rFName_input, rLName_input, rUsername_input, rPassword_Input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_module);
        Intent intent = getIntent();

        rFName_input = (EditText) findViewById(R.id.rFName_input);
        rLName_input = (EditText) findViewById(R.id.rLName_input);
        rUsername_input = (EditText) findViewById(R.id.rUsername_input);
        rPassword_Input = (EditText) findViewById(R.id.rPassword_Input);

        db = new DatabaseHelper(this);

        btn_regToAccess = (Button) findViewById(R.id.btn_regToAccess);
        btn_Register = (Button) findViewById(R.id.btn_Register);
        /*Listeners*/
        btn_regToAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String fname = rFName_input.getText().toString().trim();
                String lname = rLName_input.getText().toString().trim();
                String uname = rUsername_input.getText().toString().trim();
                String pword = rPassword_Input.getText().toString().trim();

                if (!fname.isEmpty() && !lname.isEmpty() && !uname.isEmpty()) {
                    // registerUser(name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }



}
