package com.archbrey.letters.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.archbrey.letters.DrawKeypadBox;
import com.archbrey.letters.R;

public class HeightSettings  {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    public static LinearLayout viewpadBox;
    private static int sample_pad_height;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        //com.archbrey.letters.Preferences.SettingsHolder holder;
        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.custom_keypad));
        SettingsActivity.menuArea = getR.getString(R.string.custom_keypad);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        //holder = new com.archbrey.letters.Preferences.SettingsHolder();

        menuItems = new String[4];
        menuItems[0] =getR.getString(R.string.small);
        menuItems[1] =getR.getString(R.string.medium);
        menuItems[2] =getR.getString(R.string.large);
        menuItems[3] =getR.getString(R.string.extralarge);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        sample_pad_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.keyboardHeight*4,
                rMainSettings.getDisplayMetrics());

        SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.backSelectColor);
        RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                sample_pad_height);
        viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

        SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    SettingsActivity.keyboardHeight = 38;
                    SettingsActivity.filterHeight = 5;
                    break;
                case 1:
                    SettingsActivity.keyboardHeight = 45;
                    SettingsActivity.filterHeight = 7;
                    break;
                case 2:
                    SettingsActivity.keyboardHeight = 52;
                    SettingsActivity.filterHeight = 9;
                    break;
                case 3:
                    SettingsActivity.keyboardHeight = 60;
                    SettingsActivity.filterHeight = 11;
                    break;
                default:
                    SettingsActivity.keyboardHeight = 45;
                    SettingsActivity.filterHeight = 20;
                    break;
            } //switch (position)


            SettingsActivity.prefsEditor.putInt("keyboardHeight", SettingsActivity.keyboardHeight);
            SettingsActivity.prefsEditor.putInt("filterHeight", SettingsActivity.filterHeight);
            SettingsActivity.prefsEditor.commit();


            sample_pad_height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.keyboardHeight*4,
                    rMainSettings.getDisplayMetrics());

            SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.backSelectColor);
            RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    sample_pad_height);
            viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

            SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

            SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener






} //public class HeightSettings


