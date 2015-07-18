package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.widget.GridView;

import com.archbrey.letters.LaunchpadActivity;


public class SettingsDrawer extends SettingsActivity {

    private static SettingsDrawerAdapter menuDrawerAdapter;
    private static String[] showItem;
    private static GridView settingsGridView;
    private static Context settingsContext;
    private static Resources rMainSettings;


    public SettingsDrawer (Context c, GridView getAppGridView, String[] getShowItem) {

        settingsContext = c;
        showItem = getShowItem;
        settingsGridView = getAppGridView;

        menuDrawerAdapter = new SettingsDrawerAdapter(settingsContext, showItem);
        settingsGridView.setAdapter(menuDrawerAdapter);
        settingsGridView.setBackgroundColor(LaunchpadActivity.backerColor);


    } //public SettingsDrawer (Context c, GridView getAppGridView, String[] showItem)


    public void setListener() {

      //  settingsGridView.setOnItemClickListener(new MenuClickListener(settingsContext, showItem));

    }

}
