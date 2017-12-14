package com.example.alysa.mobapde_scrapbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alysa on 12/13/2017.
 */

public class NetworkStateChecker  extends BroadcastReceiver {

    private Context context;
    private DatabaseHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced photos
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveAccount(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_USERID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_FIRST_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_LAST_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_USERNAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCT_COL_LAST_NAME))
                        );
                    } while (cursor.moveToNext());

            }
        }
    }}

    private void saveAccount(final int id, final String fname, final String lname, final String uname, final String pword) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Access_Module.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                db.updateNameStatus(id, Access_Module.NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(Access_Module.DATA_SAVED_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("name", name);
                params.put("id", Integer.toString(id));
                params.put("first_name",fname );
                params.put("last_name",lname );
                params.put("username",uname );
                params.put("password",pword );
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
