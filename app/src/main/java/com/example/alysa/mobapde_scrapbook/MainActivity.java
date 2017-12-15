package com.example.alysa.mobapde_scrapbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView androidGridView;
    Integer[] imageIDs = {
            R.drawable.email, R.drawable.mobile, R.drawable.alram,
            R.drawable.rigel, R.drawable.recycle, R.drawable.media,


    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        androidGridView = (GridView) findViewById(R.id.gridview_android_example);
        androidGridView.setAdapter(new ImageAdapterGridView(this));

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
            }
        });
    }

        //class
        public class ImageAdapterGridView extends BaseAdapter {
            private Context mContext;

            public ImageAdapterGridView(Context c) {
                mContext = c;
            }

            public int getCount() {
                return imageIDs.length;
            }

            public Object getItem(int position) {
                return null;
            }

            public long getItemId(int position) {
                return 0;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView image;

                if (convertView == null) {
                    image = new ImageView(mContext);
                    image.setLayoutParams(new GridView.LayoutParams(350, 500));
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    image.setPadding(16, 16, 16, 16);
                } else {
                    image = (ImageView) convertView;
                }
                image.setImageResource(imageIDs[position]);
                return image;
            }
        }
}

