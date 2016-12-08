package com.example.alex.mapsproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * Created by Alex on 12/6/2016.
 */
public class BookListViewCursorAdaptor extends AppCompatActivity {
    private BookListAdaptor dbHelper;
    public SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books); //make new show_books
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new country", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent addBookIntent = new Intent(BookListViewCursorAdaptor.this, AddBook.class);
                startActivity(addBookIntent);

            }
        }); */

        dbHelper = new BookListAdaptor(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllBooks();
        //Add some data
        dbHelper.insertSomeBooks();

        //Generate ListView from SQLite Database
        displayListView();



    }



    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllBooks();

        // The desired columns to be bound
        String[] columns = new String[]{

                Double.toString(BookListAdaptor.KEY_LAT),
                Double.toString(BookListAdaptor.KEY_LONG),
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
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchBooksByTitle(constraint.toString());
            }
        });
    }


    public void addBook(View view){
        Intent addBookIntent = new Intent(BookListViewCursorAdaptor.this, AddBook.class);
        startActivity(addBookIntent);
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
       // if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}



