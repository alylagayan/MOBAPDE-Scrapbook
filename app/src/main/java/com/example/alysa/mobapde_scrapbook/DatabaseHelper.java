package com.example.alysa.mobapde_scrapbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alysa.mobapde_scrapbook.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alysa on 12/3/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mobapde_db";

    public static final String ACCT_TABLE_NAME = "users";
    public static final String ACCT_COL_USERID = "id";
    public static final String ACCT_COL_FIRST_NAME = "first_name";
    public static final String ACCT_COL_LAST_NAME = "last_name";
    public static final String ACCT_COL_USERNAME = "user_name";
    public static final String ACCT_COL_PASSWORD = "pass_code";
    public static final String ACCT_COL_STATUS = "status";

    public static final String PHOTO_TABLE_NAME = "photo";
    public static final String PHOTO_COL_P_ID = "photo_id";
    public static final String PHOTO_COL_U_ID = "user_id";
    public static final String PHOTO_COL_DATA = "url";
    public static final String PHOTO_COL_TEXT = "caption";
    public static final String PHOTO_COL_DATE = "date_of_upload";
    public static final String PHOTO_COL_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccount = "CREATE TABLE " + ACCT_TABLE_NAME + "("
                                + ACCT_COL_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + ACCT_COL_FIRST_NAME + " TEXT, "
                                + ACCT_COL_LAST_NAME + " TEXT, "
                                + ACCT_COL_USERNAME + " TEXT,"
                                + ACCT_COL_PASSWORD + " TEXT,"
                                + ACCT_COL_STATUS + " TINYINT"
                                +");";
        String createPhoto = "CREATE TABLE "+ PHOTO_TABLE_NAME +"("
                             + PHOTO_COL_P_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                             + PHOTO_COL_U_ID +" INTEGER, "
                             + PHOTO_COL_DATA +" TEXT, "
                             + PHOTO_COL_DATE +" TEXT, "
                             + PHOTO_COL_STATUS +"  TINYINT"
                             +"FOREIGN KEY(" + PHOTO_COL_U_ID + ") REFERENCES "+ ACCT_TABLE_NAME + "(id) " +");";

        sqLiteDatabase.execSQL(createAccount);
        sqLiteDatabase.execSQL(createPhoto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS users";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addAccount(String f, String l, String u, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_val= new ContentValues();

        content_val.put(ACCT_COL_FIRST_NAME , f);
        content_val.put(ACCT_COL_LAST_NAME, l);
        content_val.put(ACCT_COL_USERNAME, u);
        content_val.put(ACCT_COL_PASSWORD, p);

        long result = db.insert(ACCT_TABLE_NAME, null, content_val);

        if(result == -1){
            db.close();
            return false;
        }else{
            db.close();
            return true;
        }

    }


    public boolean addAccount(String f, String l, String u, String p, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_val = new ContentValues();

        content_val.put(ACCT_COL_FIRST_NAME , f);
        content_val.put(ACCT_COL_LAST_NAME, l);
        content_val.put(ACCT_COL_USERNAME, u);
        content_val.put(ACCT_COL_PASSWORD, p);
        content_val.put(ACCT_COL_STATUS, status);


        db.insert(ACCT_TABLE_NAME, null, content_val);
        db.close();
        return true;
    }

    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCT_COL_STATUS, status);
        db.update(ACCT_TABLE_NAME, contentValues, ACCT_COL_USERID + "=" + id, null);
        db.close();
        return true;
    }

    public List<User> getAllUser() {

        String[] columns = {
                ACCT_COL_USERID,
                ACCT_COL_FIRST_NAME ,
                ACCT_COL_USERNAME,
                ACCT_COL_PASSWORD
        };

        String sortOrder =  ACCT_COL_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ACCT_TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ACCT_COL_USERID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(ACCT_COL_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(ACCT_COL_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ACCT_TABLE_NAME + " ORDER BY " + ACCT_COL_STATUS + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * this method is for getting all the unsynced name
    * so that we can sync it with database
    * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ACCT_TABLE_NAME + " WHERE " + ACCT_COL_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
