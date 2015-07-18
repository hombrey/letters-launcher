package com.archbrey.letters.Preferences;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.R;

public class ColorSettings {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    private static com.archbrey.letters.Preferences.SettingsHolder holder;
    private static String[] menuItems;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        SettingsActivity.menuArea = getR.getString(R.string.color_scheme);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();

        menuItems = new String[2];
        menuItems[0] =getR.getString(R.string.dark_scheme);
        menuItems[1] =getR.getString(R.string.light_scheme);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    LaunchpadActivity.textColor = rMainSettings.getColor(R.color.white);
                    LaunchpadActivity.backColor = rMainSettings.getColor(R.color.Black_transparent);
                    LaunchpadActivity.backerColor = rMainSettings.getColor(R.color.Blacker_transparent);
                    SettingsActivity.prefsEditor.putString ("colorscheme","black");
                    break;
                case 1:
                    LaunchpadActivity.textColor = rMainSettings.getColor(R.color.black);
                    LaunchpadActivity.backColor = rMainSettings.getColor(R.color.White_transparent);
                    LaunchpadActivity.backerColor = rMainSettings.getColor(R.color.Whiter_transparent);
                    SettingsActivity.prefsEditor.putString ("colorscheme","white");
                    break;
                default:
                    break;
            } //switch (position)


            SettingsActivity.prefsEditor.commit();
            DrawBox(mainMenuBox, settingsContext, rMainSettings);
            SettingsActivity.infoBox.setBackgroundColor(LaunchpadActivity.backerColor);
            SettingsActivity.infoView.setTextColor(LaunchpadActivity.textColor);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener




} //public class ColorSettings
