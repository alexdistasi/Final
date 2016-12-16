package com.example.alex.mapsproject;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;

/**
 * File: BookListCursorAdaptor.java
 * Authors: Alex DiStasi and Denise Fullerton
 * Date: 12/6/2016
 * Purpose: Displays entries from database in a scrolling view format
 */
public class BookListViewCursorAdaptor extends Activity {
    private BookListAdaptor dbHelper2;
    public SimpleCursorAdapter dataAdapter;
    private int themeID;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books); //make new show_books
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //create and open database
        dbHelper2 = new BookListAdaptor(this);
        dbHelper2.open();

        //Generate ListView from SQLite Database
        displayListView();
        setPreferences();
        setLayoutBackgrd();

    }



//displays a list of books/entries from the database onto screen
    private void displayListView() {

        Cursor cursor = dbHelper2.fetchAllBooks();

        // The desired columns to be bound
        String[] columns = new String[]{

                //BookListAdaptor.KEY_ROWID,
                BookListAdaptor.KEY_LAT,
                BookListAdaptor.KEY_LONG,
                BookListAdaptor.KEY_TITLE,
                BookListAdaptor.KEY_AUTHOR,
                BookListAdaptor.KEY_GENRE,

        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.lat,
                R.id.longitude,
                R.id.title,
                R.id.author,
                R.id.genre
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.book_info,
                cursor,
                columns,
                to,
                0);

        //sets listview in java to listview in the xml
        ListView listView = (ListView) findViewById(R.id.listView1);
        
        // Assign adapter to ListView
        //sets adaptor information to listview
        listView.setAdapter(dataAdapter);


        //creates on click listener for individual entries in database
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);


                String titleName =
                        cursor.getString(cursor.getColumnIndexOrThrow("title"));
                titleName = "The book owner has been notified";
                Toast.makeText(getApplicationContext(),
                        titleName, Toast.LENGTH_SHORT).show();

            }
        });


        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper2.fetchBooksByTitle(constraint.toString());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayListView();
        setPreferences();
        setLayoutBackgrd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_list_view_cursor_adaptor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
    
    //sets background color of screen depending on user preferences
    public void setLayoutBackgrd(){
        ActionBar ab=getActionBar();
        if(name!=null) {
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }
        if(themeID != 0){
            RelativeLayout settingsBkgrd = (RelativeLayout)findViewById(R.id.showBksLayout);
            if(themeID == 1){
                settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltOrange));
            }else if(themeID == 2){
                settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltblue));
            }else if(themeID == 3){
                settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.lightRed));
            }else if(themeID == 4){
                settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.green));

            }

        }
    }

    //sets preferences from user preferences
    public void setPreferences() {
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName", "");
        themeID = setPref.getInt("theme", 0);
    }
}



