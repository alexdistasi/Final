package com.example.alex.mapsproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * File: BookListAdaptor.java
 * Authors: Alex DiStasi and Denise Fullerton
 * Date: 12/6/2016
 * Purpose: -Create a sql database to store book information
 *          -Insert data from AddBook into database
 */
public class BookListAdaptor {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "long";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_GENRE = "genre";

    private static final String TAG = "BookDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "BookDB";
    private static final String SQLITE_TABLE = "Books";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    //creates a database that holds an id, latitude, longitude, title, author, and genre of a book to borrow
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_LAT + "," +
                    KEY_LONG + "," +
                    KEY_TITLE + "," +
                    KEY_AUTHOR + "," +
                    KEY_GENRE + ");";

    public static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            if (db == null)
                Log.v("onCreate: ", "database is null!");
            else
                Log.v("onCreate: ", "database exists!");
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public BookListAdaptor(Context ctx) {
        this.mCtx = ctx;
    }

    public BookListAdaptor open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //inserts data from parameters into the database
    public long createBook(int id, double myLat, double myLong, String title,String author, String genre) {

        ContentValues initialValues = new ContentValues();

        //initialValues.put(KEY_ROWID, id);
        initialValues.put(KEY_LAT, myLat);
        initialValues.put(KEY_LONG, myLong);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_AUTHOR, author);
        initialValues.put(KEY_GENRE, genre);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    //deletes all entries in database
    public boolean deleteAllBooks() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    //creates a query of items in the database that have title values that match the string value of string inputText
    public Cursor fetchBooksByTitle(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_LAT, KEY_LONG,
                            KEY_TITLE, KEY_AUTHOR, KEY_GENRE},
                    null, null, null, null, null, null);
        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID, KEY_LAT, KEY_LONG,
                            KEY_TITLE, KEY_AUTHOR, KEY_GENRE},
                    KEY_TITLE + " like '%" + inputText + "%'", null,
                    null, null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //grabs all entries from the database
    public Cursor fetchAllBooks() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_LAT, KEY_LONG,
                        KEY_TITLE, KEY_AUTHOR, KEY_GENRE},
                null, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //inserts two premade items to the database
    public void insertSomeBooks() {
        createBook(1, 42.3, 18.6, "The Stranger","Albert Camus","Comedy");
        createBook(2, 100.3, 32.6, "Art of Going With It","Alex DiStasi","Comedy");
    }



}
