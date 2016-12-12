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
 * Created by Alex on 12/4/2016.
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

    public void findBooks(View view) {
        titleStr = titleET.getText().toString();
        authorStr = authorET.getText().toString();
        genreStr = genreET.getText().toString();
        Toast.makeText(this, "PRESSED BTN" , Toast.LENGTH_SHORT).show();
        //myDB.fetchBooksByTitle(titleStr);
        //Intent launchRes = new Intent(this, FindBook.class);
        //launchRes.putExtra("DBTEST", myres);
        Intent launchQry = new Intent(this, BookQueryListView.class);
        launchQry.putExtra("titleIn", titleStr);
        //START THE ADD BOOK ACTIVITY
        startActivity(launchQry);
    }

    public void setPreferences(){
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE );

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName","");
        themeID = setPref.getInt("theme", 0);

    }
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

    public void activateMain(View view) {
        finish();
    }
}
