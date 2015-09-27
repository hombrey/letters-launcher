package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.archbrey.letters.R;


public class FilterEnable {


    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
  //  public static LinearLayout viewpadBox;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.enable_filter));
        SettingsActivity.menuArea = getR.getString(R.string.enable_filter);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;

        menuItems = new String[2];
        menuItems[0] =getR.getString(R.string.enable);
        menuItems[1] =getR.getString(R.string.disable);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();



        SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.backSelectColor);
        RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

        SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);
        if (SettingsActivity.filterEnabled) SettingsActivity.viewSample.setText(R.string.enabled);
           else SettingsActivity.viewSample.setText(R.string.disabled);

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    SettingsActivity.filterEnabled = true;
                    break;
                case 1:
                    SettingsActivity.filterEnabled = false;
                    break;
                default:
                    SettingsActivity.filterEnabled = true;
                    break;
            } //switch (position)

            SettingsActivity.SettingChanged = true;
            SettingsActivity.prefsEditor.putBoolean("FilterEnabled", SettingsActivity.filterEnabled);
            SettingsActivity.prefsEditor.commit();

            if (SettingsActivity.filterEnabled) SettingsActivity.viewSample.setText(R.string.enabled);
            else SettingsActivity.viewSample.setText(R.string.disabled);


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener



}
