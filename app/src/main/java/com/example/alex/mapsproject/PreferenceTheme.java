package com.example.alex.mapsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.util.Log;



//  4A:8F:1E:90:54:C2:85:E5:4F:F9:39:FC:74:1D:28:7C:D9:07:86:2C;com.example.alex.mapsproject

//      <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">AIzaSyAxEpK2iGJKeevUgbY0qFJWTF8YK8m5vqg</string>




/**
 * Created by MOOOoooD on 12/10/16.
 */
public class PreferenceTheme {
    private static int userTheme;
    public final static int DEFAULT = 4;
    public final static int DRAWN = 1;
    public final static int CLASSIC = 2;
    public final static int PLAIN = 3;


    // CHANGES THEME OF APPLICATION
    public static void changeToTheme(Activity activity, int theme){
        userTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }


    // CHANGES ACTIVITY THEME, USED TO SET UP APPLCATIONTHEME
    public static void onActivityCreateSetTheme(Activity activity){
        switch(userTheme){
            case DRAWN:
                //activity.setTheme(R.style.Drawing);
                activity.setTheme(R.style.Drawing);
            case CLASSIC:
                activity.setTheme(R.style.Classic);

            case PLAIN:
                activity.setTheme(R.style.Simple);

            case DEFAULT:
                activity.setTheme(R.style.Default);

                break;
        }

    }
    public static void setThemID(int themeIn){
        Log.v("in PrefTheme - theme", Integer.toString(themeIn));
        userTheme = themeIn;
    }





}
