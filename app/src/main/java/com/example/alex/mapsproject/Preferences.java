package com.example.alex.mapsproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Alex on 12/5/2016.
 */
public class Preferences extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_layout);
    }

    public void activateMain(View view) {
        finish();
    }
}
