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

    public static final String DB_NAME = "mobapde_db";

    // Table `users`
    /*  id INT AUTO_INCREMENT NOT NULL,
        user_name VARCHAR(30) NOT NULL,
        first_name VARCHAR(30) NOT NULL,
        last_name VARCHAR(30) NOT NULL,
        pass_code VARCHAR(30) NOT NULL,

        PRIMARY KEY (id)
    * */
    public static final String ACCT_TABLE_NAME = "users";
    public static final String ACCT_COL_USERID = "id";
    public static final String ACCT_COL_FIRST_NAME = "first_name";
    public static final String ACCT_COL_LAST_NAME = "last_name";
    public static final String ACCT_COL_USERNAME = "user_name";
    public static final String ACCT_COL_PASSWORD = "pass_code";
    public static final String ACCT_COL_STATUS = "status";


    //Table `photo`
    /*  photo_id INT NOT NULL,
        user_id  INT NOT NULL,
        url TEXT NOT NULL,
        caption TEXT NOT NULL,
        date_of_upload DATETIME NOT NULL,

        PRIMARY KEY (photo_id),
        CONSTRAINT user_id
            FOREIGN KEY (user_id)
            REFERENCES mobapde_db.users (id)
    * */
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

    public boolean insertAccount(String f, String l, String u, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_val= new ContentValues();

        content_val.put(ACCT_COL_FIRST_NAME , f);
        content_val.put(ACCT_COL_LAST_NAME, l);
        content_val.put(ACCT_COL_USERNAME, u);
        content_val.put(ACCT_COL_PASSWORD, p);

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

        content_val.put(ACCT_COL_FIRST_NAME , f);
        content_val.put(ACCT_COL_LAST_NAME, l);
        content_val.put(ACCT_COL_USERNAME, u);
        content_val.put(ACCT_COL_PASSWORD, p);
        content_val.put(ACCT_COL_STATUS, status);


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
        contentValues.put(ACCT_COL_STATUS, status);
        db.update(ACCT_TABLE_NAME, contentValues, ACCT_COL_USERID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + ACCT_TABLE_NAME + " ORDER BY " + ACCT_COL_USERID + " ASC;";
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
