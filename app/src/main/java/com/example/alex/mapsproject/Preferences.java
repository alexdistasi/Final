package com.example.alex.mapsproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;
import android.widget.RelativeLayout;


/**
 * Created by Alex on 12/5/2016.
 * modifyed by Denise on 12/10/16
 * 
 * This is the java class for settings activity 
 * user preferences are set in this activity 
 *      onClick methods that are set to buttons in the activity to change background color
 *      an EditText widget is implemented to take user input for name
 *      
 *      the user preferences are stored in a shared preference object for the app 
 */
public class Preferences extends Activity {


    public int savePref;
    public String userName;
    private ImageView preview;
    private EditText nameBox;
    private int themeID;
    private String name;
    RelativeLayout settingsBkgrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get user name from shared preferences, if name stored
        getUserName();
        
        // sets activity layout view
        setContentView(R.layout.preferences_layout);
        
        // calls function to initialize widget objects and register listeners 
        initializeAndRegister();
        
        // sets the user preferences to app
        setPreferences();
        
        // sets layout background color based on shared preferences
        setLayoutBackgrd();
    }

    // initializing widget objects and regstering listeners
    private void initializeAndRegister(){
        nameBox = (EditText) findViewById(R.id.nameInput); // sets edit text widget for name to nameBox variable
        nameBox.addTextChangedListener(nameInput);// retister listener for focus in edit text box passing the textwatcher pointer 
        settingsBkgrd = (RelativeLayout)findViewById(R.id.prefLayout);// registers RelativeLayout object -background color changed in this object
        // sets user name if username is null
        if(userName!=null) {
            nameBox.setText(userName);
        }
    }

    // TextWatcher object to listen for focus in editText box for user name
    private TextWatcher nameInput = new TextWatcher() {

        @Override// only using onTextChanged callback method to store user name
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            userName = s.toString();// variable stores user name
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

   //onClick Method to set light orange color background
    public void setting_1_Img(View view){
        savePref = 1; // variable represents color choice
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltOrange));//sets background color 

        savePreferences(); // method call to store background color preference
        Log.v("savePref Orange ", Integer.toString(savePref));

        //preview.setImageResource(R.drawable.ic_bksgreen);
    }

    //onClick Method to set light blue color background
    public void setting_2_Img(View view){
        savePref = 2;// variable represents color choice
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltblue));//sets background color 
        savePreferences();// method call to store background color preference
        Log.v("savePref blue ", Integer.toString(savePref));

    }
    
    //onClick Method to set light pink background
    public void setting_3_Img(View view){
        savePref = 3;// variable represents color choice
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.lightRed));//sets background color 
        savePreferences();// method call to store background color preference
        Log.v("savePref red ", Integer.toString(savePref));
    }

    //onClick Method to set green color background
    public void setting_4_img(View view){
        savePref = 4;// variable represents color choice
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.green));//sets background color 
        savePreferences();// method call to store background color preference
        Log.v("savePref green ", Integer.toString(savePref));
    }

    // saves user name in preferences
    public void getUserName(){
        SharedPreferences getTheName = this.getSharedPreferences("UserPreferences",Context.MODE_PRIVATE);
        userName = getTheName.getString("UserName",null);
    }


    // saving SharedPreferences
    public void savePreferences(){
        // instantiates shared preferences object  - stores as UserPreferences and sets the mode to private to only be available to this app
        SharedPreferences preferences = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        // editor object to pass information in shared preferences
        SharedPreferences.Editor editor = preferences.edit();
        // stores username entered in shared preference
        editor.putString("UserName",userName);
        // stores savePref selection from onClick methods in shared prefence
        editor.putInt("theme",savePref);
        // commits storing values
        editor.commit();

    }

    // sets background based on onClick themeID variable value from shared preferences
    public void setLayoutBackgrd(){
        if(themeID != 0){
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
        
        ActionBar ab=getActionBar(); // actionbar object to set user name to actionbar
        if(name!=null) {// name stored, set name to action bar with app title
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }
    }

    // sets preferences from SharedPreferences to app
    public void setPreferences() {
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName", "");
        themeID = setPref.getInt("theme", 0);
    }
}
