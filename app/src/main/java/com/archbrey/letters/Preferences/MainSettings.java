package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;


public class MainSettings {

    private static SettingsDrawerAdapter menuDrawerAdapter;
    private static String[] showItem;
    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    private static com.archbrey.letters.Preferences.SettingsHolder holder;
    private static String[] menuItems;

    public MainSettings(){

    }


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();


        menuItems = new String[3];
        menuItems[0] =getR.getString(R.string.color_scheme);
        menuItems[1] =getR.getString(R.string.custom_filter);
        menuItems[2] =getR.getString(R.string.custom_keypad);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()


    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}

    private class MenuClickListener implements AdapterView.OnItemClickListener {

        //private  Context clickListenerContext;
        /*
        public MenuClickListener (){
           // clickListenerContext = c;
        } //public DrawerClickListener(Context c, MainActivity.AppItem[] appItem, PackageManager pm)
        */

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //Intent launchIntent = pmForListener.getLaunchIntentForPackage(appItemForListener[position].pkgname);
            settingsContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS) );

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


} //public class MainSettings
