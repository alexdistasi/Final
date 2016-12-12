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

        // get user name
        getUserName();
        setContentView(R.layout.preferences_layout);
        initializeAndRegister();
        setPreferences();
        setLayoutBackgrd();

        //preview = (ImageView) findViewById(R.id.previewSetting);
    }

    private void initializeAndRegister(){
        nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.addTextChangedListener(nameInput);
        settingsBkgrd = (RelativeLayout)findViewById(R.id.prefLayout);
        if(userName!=null) {
            nameBox.setText(userName);
        }
    }
    /****
     * TextWatcher class - to listen for text changes in Edit text box
     *     method used -
     *         public void onTextChanged(CharSequence s, int start, int before, int count)
     *         parameters: CharSequence s - takes characters from softkeyboard
     *                     int start - start of string sequence
     *                     int before - length of text before
     *                     int count - counting the characters s
     *                used in store answer input by user
     */
    // TextWatcher listener to collect string input from user
    private TextWatcher nameInput = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //           clearPrefName();
            // need to store answer to question
            userName = s.toString();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


//    @Override
//    protected void onPause(){
//        super.onPause();
//        savePreferences();
//
//    }


    public void setting_1_Img(View view){
        savePref = 1;
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltOrange));

        savePreferences();
        Log.v("savePref Drawn ", Integer.toString(savePref));

        //preview.setImageResource(R.drawable.ic_bksgreen);
    }

    public void setting_2_Img(View view){
        savePref = 2;
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.ltblue));

        savePreferences();
        //preview.setImageResource(R.drawable.ic_bkblue);
        Log.v("savePref Classic ", Integer.toString(savePref));

    }

    public void setting_3_Img(View view){
        savePref = 3;
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.lightRed));

        savePreferences();
        Log.v("savePref Plain ", Integer.toString(savePref));

        //preview.setImageResource(R.drawable.ic_bksorange);
    }

    public void setting_4_img(View view){
        savePref = 4;
        settingsBkgrd.setBackgroundColor(getResources().getColor(R.color.green));

        savePreferences();
        Log.v("savePref default ", Integer.toString(savePref));

        //preview.setImageResource(R.drawable.ic_bksred);
    }

    public void getUserName(){
        SharedPreferences getTheName = this.getSharedPreferences("UserPreferences",Context.MODE_PRIVATE);
        userName = getTheName.getString("UserName",null);
    }


    // saving preferences
    public void savePreferences(){
        SharedPreferences preferences = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName",userName);
        editor.putInt("theme",savePref);
        editor.commit();

    }

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
        ActionBar ab=getActionBar();
        if(name!=null) {
            ab.setTitle(getResources().getString(R.string.app_name) + " - " + name);
        }
    }

    public void setPreferences() {
        //create instance of SharedPreferences
        SharedPreferences setPref = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        //getfunctions to get data stored in sharedpref file
        name = setPref.getString("UserName", "");
        themeID = setPref.getInt("theme", 0);
    }

    public void activateMain(View view) {
        finish();
    }
}
