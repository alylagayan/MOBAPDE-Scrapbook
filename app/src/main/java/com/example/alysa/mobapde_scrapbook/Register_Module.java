package com.example.alysa.mobapde_scrapbook;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import android.util.Log;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alysa on 12/3/2017.
 */

public class Register_Module extends Activity {

    private DatabaseHelper db;
    private SessionManager session;

    private static final String TAG = Register_Module.class.getSimpleName();
    private Button btn_regToAccess, btn_Register;
    private EditText rFName_input, rLName_input, rUsername_input, rPassword_Input;
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_module);
        Intent intent = getIntent();

        rFName_input = (EditText) findViewById(R.id.rFName_input);
        rLName_input = (EditText) findViewById(R.id.rLName_input);
        rUsername_input = (EditText) findViewById(R.id.rUsername_input);
        rPassword_Input = (EditText) findViewById(R.id.rPassword_Input);
        btn_regToAccess = (Button) findViewById(R.id.btn_regToAccess);
        btn_Register = (Button) findViewById(R.id.btn_Register);

        db = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent i = new Intent(Register_Module.this,
                    App_Module.class);
            startActivity(i);
            finish();
        }

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

                if (!fname.isEmpty() && !lname.isEmpty() && !uname.isEmpty() &&!pword.isEmpty()) {
                     registerUser(fname, lname, uname, pword);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void registerUser(final String fname, final String lname, final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("user_name");
                        String firstname = user.getString("first_name");
                        String lastname= user.getString("last_name");
                        String password = user.getString("pass_word");

                        // Inserting row in users table
                        db.addUser(fname, lname, username, password);

                        Toast.makeText(getApplicationContext(), "User successfully registered!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Register_Module.this, Access_Module.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", username);
                params.put("first_name", fname);
                params.put("last_name",  lname);
                params.put("pass_word", password);

                return params;
            }

        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
