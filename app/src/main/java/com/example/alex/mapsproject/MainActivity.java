package com.example.alex.mapsproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * File: MainActivity.java
 * Author: Alex DiStasi and Denise Fullerton
 * Date: 12/4/2016
 * Purpose: Act as Model for communcation between activities and database
 */
public class MainActivity extends Activity {

    private String name;
    private int themeID;


    @Override  // instantiates actionbar and sets user settings onCreate, if settings are saved
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferences();
        ActionBar ab=getActionBar();
        if(name!=null) {
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }
        setContentView(R.layout.activity_main);
        setLayoutBackgrd();
    }

    @Override // sets layout based on Shared Preferences on resume -
    protected void onResume(){
        super.onResume();
        setPreferences();
        Log.v("in Main - theme number ",Integer.toString(themeID));
        setContentView(R.layout.activity_main);
        setLayoutBackgrd();
    }


    // onclick method to launch MapsActivity - AddBook is called from this activity
    public void launchAddBook(View view) {
        //CREATE AN INTENT TO DISPLAY THE ADD BOOK ACTIVITY
        Intent launchAddBook = new Intent(this, MapsActivity.class);
        //START THE ADD BOOK ACTIVITY
        startActivity(launchAddBook);
    }

    // onclick method to launch FindBooks - user can enter search values in this activity
    public void launchFindBook(View view) {
        //CREATE AN INTENT TO DISPLAY THE FIND BOOK ACTIVITY
        Intent launchFindBook = new Intent(this, FindBook.class);
        //START THE FIND BOOK ACTIVITY
        startActivity(launchFindBook);
    }
    
    // onclick method to launch show_books - Adaptor view for stored books 
    public void launchShowBooks(View view){
        //CREATE AN INTENT TO DISPLAY THE FIND BOOK ACTIVITY
        Intent launchBook = new Intent(this, BookListViewCursorAdaptor.class);
        //START THE FIND BOOK ACTIVITY
        startActivity(launchBook);
    }

    // sets background based on theme from SharedPreferences
    public void setLayoutBackgrd(){
        if(themeID != 0){
            RelativeLayout setMainBkgrd = (RelativeLayout)findViewById(R.id.actMain);
            if(themeID == 1){
                setMainBkgrd.setBackgroundColor(getResources().getColor(R.color.ltOrange));

            }else if(themeID == 2){
                setMainBkgrd.setBackgroundColor(getResources().getColor(R.color.ltblue));

            }else if(themeID == 3){
                setMainBkgrd.setBackgroundColor(getResources().getColor(R.color.lightRed));

            }else if(themeID == 4){
                setMainBkgrd.setBackgroundColor(getResources().getColor(R.color.green));


            }

        }
    }

    @Override// inflate actionbar - 
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override // Intent to launch Preferences activity where settings can be set by user
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.usersetting){
            Intent launchPref = new Intent(this, Preferences.class);
            //START THE SETTINGS ACTIVITY
            startActivity(launchPref);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // create instance of SharedPreferences - used onCreate to establish user settings
    public void setPreferences(){
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE );
        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName","");
        themeID = setPref.getInt("theme", 0);

    }




}
