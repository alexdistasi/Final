package com.example.alex.mapsproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookDB";
    private static final String DATABASE_TABLE = "book_Items";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_TASK_ID = "_id";
    private static final LatLng KEY_LOC = new LatLng(0,0);
    private static double KEY_LAT = 0;
    private static double KEY_LONG = 0;
    private static String KEY_TITLE = "Title";
    private static String KEY_AUTHOR = "Author";
    private static String KEY_GENRE = "Genre";

    public String returnStr;


    private int taskCount;

    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY, "
                + KEY_LAT + " REAL, "
                + KEY_LONG+ " REAL, "
                + KEY_TITLE + " TEXT, "
                + KEY_AUTHOR + " TEXT, "
                + KEY_GENRE + " TEXT" + ")";
        database.execSQL (table);
        taskCount = 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    public String testDB(){
        String res = KEY_AUTHOR + " " + KEY_GENRE + " " + KEY_TITLE + " " + KEY_LONG + " " + KEY_LAT;
        return res;
    }

    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE

    public void addBook(BookLoc_Itm book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        taskCount++;
        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        values.put(KEY_TASK_ID, taskCount);

        values.put(Double.toString(KEY_LAT), book.getLat());

        values.put(Double.toString(KEY_LONG), book.getLongi());


        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        values.put(KEY_TITLE, book.getTitle()); // task name

        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        values.put(KEY_AUTHOR, book.getAuthor()); // task name

        //ADD KEY-VALUE PAIR INFORMATION FOR IS_DONE
        //  0- NOT DONE, 1 - IS DONE
        values.put(KEY_GENRE, book.getGenre());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }


    public void editTaskItem(BookLoc_Itm book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Double.toString(KEY_LAT), book.getLat());
        values.put(Double.toString(KEY_LONG), book.getLongi());
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_GENRE, book.getGenre());

        db.update(DATABASE_TABLE, values, KEY_TASK_ID + " = ?",
                new String[]{
                        String.valueOf(book.getId())
                });
        db.close();
    }

    public BookLoc_Itm getBooks(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                //new String[]{KEY_TASK_ID, KEY_LAT, KEY_LONG, KEY_TITLE, KEY_AUTHOR, KEY_GENRE},
                new String[]{KEY_TASK_ID, Double.toString(KEY_LAT), Double.toString(KEY_LONG), KEY_TITLE, KEY_AUTHOR, KEY_GENRE},
                KEY_TASK_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        BookLoc_Itm books = new BookLoc_Itm(
                cursor.getInt(0),
                cursor.getDouble(1),
                cursor.getDouble(2),
                cursor.getString(3),    //author
                cursor.getString(4),    //genre
                cursor.getString(5));

        db.close();

        //returnStr = books.toString();
        //returnStr = KEY_TASK_ID + Double.toString(KEY_LAT) + Double.toString(KEY_LONG) + KEY_TITLE + KEY_AUTHOR + KEY_GENRE;
        return books;

    }


    public String getString(BookLoc_Itm book){
        returnStr = KEY_TASK_ID + Double.toString(KEY_LAT) + Double.toString(KEY_LONG) + KEY_TITLE + KEY_AUTHOR + KEY_GENRE;
        return returnStr;
    }


}
