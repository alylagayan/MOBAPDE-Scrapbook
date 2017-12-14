package com.example.alysa.mobapde_scrapbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alysa.mobapde_scrapbook.models.User;

import java.util.List;

public class Access_Module extends AppCompatActivity {

    private Button btn_Login, btn_Register;
    private EditText username_input, password_input;

    private DatabaseHelper db;

    private List<User> accounts;

    public static final String URL_SAVE_NAME = "http://192.168.0.14/MOBAPDE-Scrapbook/php/saveName.php";
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "net.alysa.data_saved";

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access__module);

        username_input = (EditText) findViewById(R.id.username_input);
        password_input = (EditText) findViewById(R.id.password_input);

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

        //registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }

    private void loadNames() {
        accounts.clear();
        Cursor cursor = db.getAllUser();
        if (cursor.moveToFirst()) {
            do {
                User account = new User();

                account.setFirstName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_FIRST_NAME)));
                account.setLastName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_LAST_NAME)));
                account.setUserName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_USERNAME)));
                account.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_PASSWORD)));
                account.setID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_STATUS)));

                accounts.add(account);
            } while (cursor.moveToNext());
        }

    }

    private void launchApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void registerInApp() {
        Intent intent = new Intent(this, Register_Module.class);
        startActivity(intent);
    }

    private void emptyInputEditText() {
        username_input.setText(null);
        password_input.setText(null);
    }


}
