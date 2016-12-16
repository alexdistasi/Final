package com.example.alex.mapsproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;



/**
 * File: AddBook.java
 * Created by Alex on 12/4/2016.
 * Purpose: This class is used to add data from three edit text files and two doubles representing location to a database
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
    private int themeID;
    private String name;


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
        titleET = (EditText)findViewById(R.id.AtitleET);
        authorET = (EditText)findViewById(R.id.AuthorET);
        genreET = (EditText)findViewById(R.id.GenreET);

        setPreferences();
        ActionBar ab=getActionBar();
        if(name!=null) {
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }

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
        setLayoutBackgrd();
    }

    //When submit button is pressed, data is grabbed from the three EditText boxes and then added to a DB
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
    //changes background color depending on user's preference
    public void setLayoutBackgrd(){
        if(themeID != 0){
            RelativeLayout setBkgrd = (RelativeLayout)findViewById(R.id.addBkLayout);
            if(themeID == 1){
                setBkgrd.setBackgroundColor(getResources().getColor(R.color.ltOrange));
            }else if(themeID == 2){
                setBkgrd.setBackgroundColor(getResources().getColor(R.color.ltblue));
            }else if(themeID == 3){
                setBkgrd.setBackgroundColor(getResources().getColor(R.color.lightRed));
            }else if(themeID == 4){
                setBkgrd.setBackgroundColor(getResources().getColor(R.color.green));

            }

        }
    }
    public void setPreferences(){
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE );

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName","");
        themeID = setPref.getInt("theme", 0);

    }


}
