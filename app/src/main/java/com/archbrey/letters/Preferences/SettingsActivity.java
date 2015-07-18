package com.archbrey.letters.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.R;

public class SettingsActivity extends Activity {

    public Context C;
    private Resources r;
    private static LinearLayout settingsScreen;

    private static LinearLayout infoBox;
    private View drawerBox ;

    private static GridView gridDrawer;
    private static MainSettings mainsettingsHandle;
    //private LinearLayout setColorsBox;
    private com.archbrey.letters.Preferences.SettingsHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait

        r = getResources();
        C = this;
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


    private void assembleScreen(){


        infoBox = new LinearLayout(this);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        infoBox.setBackgroundColor(LaunchpadActivity.backerColor);
        TextView infoView;
        infoView = new TextView(this);
        infoView.setText("Settings");
        infoView.setGravity(Gravity.CENTER_HORIZONTAL);
        infoView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        infoView.setTextColor(LaunchpadActivity.textColor);

        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, //width
                LinearLayout.LayoutParams.WRAP_CONTENT); //height
                infoViewParams.setMargins(0,20,0,20);
        infoBox.addView(infoView,infoViewParams);

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
