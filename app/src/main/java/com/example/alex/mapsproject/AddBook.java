package com.example.alex.mapsproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alex on 12/4/2016.
 */
public class AddBook extends Activity {

    private EditText titleET;
    private EditText authorET;
    private EditText genreET;
    private String titleStr;
    private String authorStr;
    private String genreStr;
    private double myLongitude;
    private double myLat;

    public BookListAdaptor myDB;

    private int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        Intent bookLocation = getIntent();
        //retrieves data from an intent in main

        myDB = new BookListAdaptor(this);
        myDB.open();
        //myDB.deleteAllBooks();
        //myDB.insertSomeBooks();

        myLongitude = bookLocation.getDoubleExtra("LongPoint", 0.0);
        myLat = bookLocation.getDoubleExtra("LatPoint", 0.0);
        titleET = (EditText)findViewById(R.id.ftitleET);
        authorET = (EditText)findViewById(R.id.AuthorET);
        genreET = (EditText)findViewById(R.id.GenreET);

        titleET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                titleStr=titleET.getText().toString();
            }
        });
        authorET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                authorStr=authorET.getText().toString();
            }
        });
        genreET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                genreStr=genreET.getText().toString();
            }
        });
    }

    public void onSubmit(View view){
        titleStr=titleET.getText().toString();
        authorStr=authorET.getText().toString();
        genreStr=genreET.getText().toString();
        //database = new DBHelper(this);

        //BookLoc_Itm bookIn = new BookLoc_Itm(id, myLongitude, myLat, titleStr, authorStr, genreStr);

        //database.addBook(bookIn);


        myDB.createBook(id, myLongitude, myLat, titleStr, authorStr, genreStr);
        id++;

        String myres = titleStr + " " + authorStr + " " + genreStr + " " + myLat;
        //BookLoc_Itm x= database.getBooks(id); //model class
        //myres = database.getString();

        Toast.makeText(this, myres , Toast.LENGTH_SHORT).show();
        //Intent launchRes = new Intent(this, FindBook.class);
        //launchRes.putExtra("DBTEST", myres);
        Intent launchHome = new Intent(this, MainActivity.class);
        //START THE ADD BOOK ACTIVITY
        startActivity(launchHome);
    }


}