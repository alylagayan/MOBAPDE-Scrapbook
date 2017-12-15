package com.example.alysa.mobapde_scrapbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Alysa on 12/3/2017.
 */

public class AccountTab extends Fragment{

    private Button btn_Logout;
    private DatabaseHelper db;
    private SessionManager session;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_app_module, container, false);
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);
        return inflater.inflate(R.layout.fragment_tab_account_module, container, false);

        // SqLite database handler
        db = new DatabaseHelper(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        btn_Logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(App_Module.this, Access_Module.class);
        startActivity(intent);
        finish();
    }


}
