package com.example.alysa.mobapde_scrapbook;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_app_module, container, false);
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);
        return inflater.inflate(R.layout.fragment_tab_account_module, container, false);


    }


}
