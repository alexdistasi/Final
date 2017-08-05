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
 * File: FindBook.java
 * Author: Alex DiStasi and Denise Fullerton
 * Date: 12/4/2016
 * Purpose: Find objects in the DB (book titles) that match an input string (from edittext box) using a sqlite query
 */
public class FindBook extends Activity{
    private EditText titleET;
    private EditText authorET;
    private EditText genreET;
    String titleStr;
    String authorStr;
    String genreStr;
    public BookListAdaptor myDB;
    private String name;
    private int themeID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_book);
        myDB = new BookListAdaptor(this);
        myDB.open();
        titleStr=""; authorStr=""; genreStr="";

        titleET = (EditText)findViewById(R.id.ftitleET);
        authorET = (EditText)findViewById(R.id.fauthorET);
        genreET = (EditText)findViewById(R.id.fgenreET);

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

        setPreferences();
        setLayoutBackgrd();
        ActionBar ab=getActionBar();
        if(name!=null) {
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }
    }

    //on click, information is grabbed from edittext boxes to be used for a query in the database 
   
    /*
    Function: findBooks
    Parameters: view
    Purpose: Grab data from 'Find Book' edittext boxes and run it through a query connected to app's database to
            find entries that match the user's input. Then, display the results of the query in a ListView.
    */
    public void findBooks(View view) {
        titleStr = titleET.getText().toString();
        authorStr = authorET.getText().toString();
        genreStr = genreET.getText().toString();
        Intent launchQry = new Intent(this, BookQueryListView.class);
        launchQry.putExtra("titleIn", titleStr);
        startActivity(launchQry);
    }

    //gets user preferences
    public void setPreferences(){
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE );

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName","");
        themeID = setPref.getInt("theme", 0);

    }
    //sets background based on preferences
    public void setLayoutBackgrd(){
        if(themeID != 0){
            RelativeLayout setBkgrd = (RelativeLayout)findViewById(R.id.findLayout);
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

    //returns to main
    public void activateMain(View view) {
        finish();
    }
}
