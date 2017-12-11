package com.example.alysa.mobapde_scrapbook;

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

public class Register_Module extends AppCompatActivity {

    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";
    private BroadcastReceiver broadcastReceiver;

    public static final String URL_SAVE_NAME = "http://172.16.6.175/scraapbook/insertAccount.php";
    private DatabaseHelper db;

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
                    registerUser(name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    private void addEntry(String fname, String lname, String Gen, String uname, String pass, String email)
    {

        SQLiteDatabase DB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstname", fname);
        values.put("lastname", lname);
        values.put("gender", Gen);
        values.put("username", uname);
        values.put("password", pass);
        values.put("email", email);

        try
        {
            DB.insert(DatabaseHelper.ACCT_TABLE_NAME, null, values);

            Toast.makeText(getApplicationContext(), "your details submitted Successfully...", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
