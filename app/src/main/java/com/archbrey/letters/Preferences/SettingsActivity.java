package com.archbrey.letters.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.R;

public class SettingsActivity extends Activity {

    public Context C;
    private Resources r;
    private static LinearLayout settingsScreen;

    public static LinearLayout infoBox;
    public static TextView infoView;

    private View drawerBox ;

    private static GridView gridDrawer;
    private static MainSettings mainsettingsHandle;
    //private LinearLayout setColorsBox;
    private com.archbrey.letters.Preferences.SettingsHolder holder;

    public static int menuLevel;
    public static String menuArea;

    public static SharedPreferences.Editor prefsEditor;
    public static SharedPreferences prefs;
    public static String prefName = "LettersPrefs";

    public static int textColor;
    public static int backColor;
    public static int backerColor;
    public static int textSelectColor;
    public static int backSelectColor;
    public static int drawerColumns;
    public static int drawerTextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        r = getResources();
        C = this;
        menuLevel = 0;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();
        holder.setSettingsContext(this);
        holder.setResources(r);

        settingsScreen = new LinearLayout(this);
        settingsScreen.setOrientation(LinearLayout.VERTICAL);
        settingsScreen.setGravity(Gravity.BOTTOM);

        //draw menu drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

        assembleScreen();

        setContentView(settingsScreen);

        gridDrawer = (GridView) findViewById(R.id.drawer_content);


        mainsettingsHandle = new MainSettings();
        mainsettingsHandle.DrawBox(gridDrawer, this, r);


    } // protected void onCreate(Bundle savedInstanceState)


    @Override
    protected void onResume() {
        super.onResume();

    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

    } //protected void onStart()

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if( (keyCode == KeyEvent.KEYCODE_BACK) && (menuLevel>0) ) {
            if (menuLevel ==1) {
                //mainsettingsHandle = new MainSettings();
                mainsettingsHandle.DrawBox(gridDrawer, this, r);
                menuLevel --;
                return true;
                 } //if (menuLevel ==1)

          // onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void assembleScreen(){


        infoBox = new LinearLayout(this);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        infoBox.setBackgroundColor(SettingsActivity.backerColor);

        infoView = new TextView(this);
        infoView.setText("Settings");
        infoView.setGravity(Gravity.CENTER_HORIZONTAL);
        infoView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        infoView.setTextColor(SettingsActivity.textColor);

        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, //width
                LinearLayout.LayoutParams.WRAP_CONTENT); //height
                infoViewParams.setMargins(0, 20, 0, 20);
        infoBox.addView(infoView, infoViewParams);

        /*
        setColorsBox = new LinearLayout(this);
        setColorsBox.setOrientation(LinearLayout.HORIZONTAL);
        setColorsBox.setBackgroundColor(LaunchpadActivity.backerColor);
        TextView dummyText;
        dummyText = new TextView(this);
        dummyText.setText("this is a dummy");
        dummyText.setGravity(Gravity.CENTER);
        dummyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        dummyText.setTextColor(LaunchpadActivity.textColor);
        setColorsBox.addView(dummyText);*/

        /*
        drawerBox.setId(R.id.settingsDrawer);
        setColorsBox.setId(R.id.setColorsBox);
        infoBox.setId(R.id.bottomRowBox);*/


        settingsScreen.addView(drawerBox);
        settingsScreen.addView(infoBox);



    }


}
