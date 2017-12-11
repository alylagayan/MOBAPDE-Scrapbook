package com.example.alysa.mobapde_scrapbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Alysa on 12/3/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DB_NAME = "mobapde_db";

    public static final String ACCT_TABLE_NAME = "users";
    public static final String ACCT_COLUMN_USERID = "id";
    public static final String ACCT_COLUMN_FIRST_NAME = "first_name";
    public static final String ACCT_COLUMN_LAST_NAME = "last_name";
    public static final String ACCT_COLUMN_USERNAME = "user_name";
    public static final String ACCT_COLUMN_PASSWORD = "pass_code";
    public static final String ACCT_COLUMN_STATUS = "status";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + ACCT_TABLE_NAME + "("
                + ACCT_COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACCT_COLUMN_FIRST_NAME + " TEXT, "
                + ACCT_COLUMN_LAST_NAME + " TEXT, "
                + ACCT_COLUMN_USERNAME + " TEXT,"
                + ACCT_COLUMN_PASSWORD + " TEXT,"
                + ACCT_COLUMN_STATUS + " TINYINT"
                +");";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS users";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean insertAccount(String f, String l, String u, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_val= new ContentValues();

        content_val.put(ACCT_COLUMN_FIRST_NAME , f);
        content_val.put(ACCT_COLUMN_LAST_NAME, l);
        content_val.put(ACCT_COLUMN_USERNAME, u);
        content_val.put(ACCT_COLUMN_PASSWORD, p);

        long result = db.insert(ACCT_TABLE_NAME, null, content_val);

        if(result == -1){
            return false;
        }else
            return true;
    }

    /*
    * This method is taking two arguments
    * first one is the name that is to be saved
    * second one is the status
    * 0 means the name is synced with the server
    * 1 means the name is not synced with the server
    **/
    public boolean addAccount(String f, String l, String u, String p, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_val = new ContentValues();

        content_val.put(ACCT_COLUMN_FIRST_NAME , f);
        content_val.put(ACCT_COLUMN_LAST_NAME, l);
        content_val.put(ACCT_COLUMN_USERNAME, u);
        content_val.put(ACCT_COLUMN_PASSWORD, p);
        content_val.put(ACCT_COLUMN_STATUS, status);


        db.insert(ACCT_TABLE_NAME, null, content_val);
        db.close();
        return true;
    }


    /*
    * This method taking two arguments
    * first one is the id of the name for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCT_COLUMN_STATUS, status);
        db.update(ACCT_TABLE_NAME, contentValues, ACCT_COLUMN_USERID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ACCT_TABLE_NAME + " ORDER BY " + ACCT_COLUMN_USERID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * this method is for getting all the unsynced name
    * so that we can sync it with database
    * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ACCT_TABLE_NAME + " WHERE " + ACCT_COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
