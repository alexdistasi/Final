package com.example.alex.mapsproject;

import android.content.Intent;
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
import android.widget.Toast;

/**
 * Created by Alex on 12/10/2016.
 */
public class BookQueryListView extends AppCompatActivity{
    private BookListAdaptor dbHelper2;
    public SimpleCursorAdapter dataAdapter;
    public  String qryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books); //make new show_books

        dbHelper2 = new BookListAdaptor(this);
        dbHelper2.open();

        Intent qryBooks = getIntent();

        //retrieves data from an intent in main
        qryTitle=qryBooks.getStringExtra("titleIn");
        //Generate ListView from SQLite Database
        displayListView();

    }


    private void displayListView() {

        Cursor cursor = dbHelper2.fetchBooksByTitle(qryTitle);

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

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView

        //LINEE THAT BREAKS
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);


                String titleName = cursor.getString(cursor.getColumnIndexOrThrow("title"));
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
}
