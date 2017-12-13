package com.example.alysa.mobapde_scrapbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

            }
        }
    }
}
