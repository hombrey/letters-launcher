package com.archbrey.letters.Preferences;

import android.content.Context;
import android.widget.GridView;



public class SettingsDrawer extends SettingsActivity {



    public SettingsDrawer (Context c, GridView getAppGridView, String[] getShowItem) {

        SettingsDrawerAdapter menuDrawerAdapter;
        String[] showItem;
        GridView settingsGridView;
        Context settingsContext;


        settingsContext = c;
        showItem = getShowItem;
        settingsGridView = getAppGridView;

        menuDrawerAdapter = new SettingsDrawerAdapter(settingsContext, showItem);
        settingsGridView.setAdapter(menuDrawerAdapter);
        settingsGridView.setBackgroundColor(SettingsActivity.backerColor);
        settingsGridView.setNumColumns(SettingsActivity.drawerColumns);



    } //public SettingsDrawer (Context c, GridView getAppGridView, String[] showItem)



}
