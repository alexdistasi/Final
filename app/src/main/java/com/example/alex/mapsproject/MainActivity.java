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

public class MainActivity extends Activity {

    private String name;
    private int themeID;


    @Override
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

    @Override
    protected void onResume(){
        super.onResume();
        setPreferences();
        Log.v("in Main - theme number ",Integer.toString(themeID));
        setContentView(R.layout.activity_main);
        setLayoutBackgrd();
    }


    public void launchAddBook(View view) {
        //CREATE AN INTENT TO DISPLAY THE ADD BOOK ACTIVITY
        Intent launchAddBook = new Intent(this, MapsActivity.class);
        //START THE ADD BOOK ACTIVITY
        startActivity(launchAddBook);
    }

    public void launchFindBook(View view) {
        //CREATE AN INTENT TO DISPLAY THE FIND BOOK ACTIVITY
        Intent launchFindBook = new Intent(this, FindBook.class);
        //START THE FIND BOOK ACTIVITY
        startActivity(launchFindBook);
    }

    public void launchShowBooks(View view){
        //CREATE AN INTENT TO DISPLAY THE FIND BOOK ACTIVITY
        Intent launchBook = new Intent(this, BookListViewCursorAdaptor.class);
        //START THE FIND BOOK ACTIVITY
        startActivity(launchBook);
    }
//    public void launchPreferences(View view) {
//
//        //CREATE AN INTENT TO DISPLAY THE SETTINGS ACTIVITY
//        Intent launchPref = new Intent(this, Preferences.class);
//        //START THE SETTINGS ACTIVITY
//        startActivity(launchPref);
//    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override
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


    // save preferences
    public void setPreferences(){
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE );

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName","");
        themeID = setPref.getInt("theme", 0);

    }




}
