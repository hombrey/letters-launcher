package com.archbrey.letters;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.archbrey.letters.Preferences.SettingsActivity;


public class OptionsCall {

    public static GridView mainMenuBox;
    private static Context mainContext;
    private static Resources rMainSettings;


    public GridView DrawPrimary (GridView getgridBox,Context c,Resources getR) {

        AppItem[] menuItems;

      //  SettingsActivity.infoView.setText(getR.getString(R.string.settings));
        mainMenuBox = getgridBox;
        mainContext = c;
        rMainSettings = getR;

        menuItems = new AppItem[3];
        for (int menuInc=0; menuInc<menuItems.length; menuInc++) menuItems[menuInc] = new AppItem();

        menuItems[0].label=getR.getString(R.string.action_system_settings);
        menuItems[1].label=getR.getString(R.string.action_launcher_settings);
        menuItems[2].label=getR.getString(R.string.action_wallpaper);

        LaunchpadActivity.drawDrawerBox.DrawBox(menuItems);
        setListenerPrimary();

        return mainMenuBox;

    } //public LinearLayout DrawPrimary ()


    public void setListenerPrimary () {
        mainMenuBox.setOnItemClickListener(new MenuPrimaryClickListener());
        mainMenuBox.setOnItemLongClickListener(new MenuLongClickNuller());
    }//public void setListenerPrimary ()


    private class MenuPrimaryClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            switch (position) {
                case 0:
                    DrawSecondary (mainMenuBox,mainContext,rMainSettings);
                    break;
                case 1:
                    SettingsActivity.menuArea="MainSettings";
                    final Intent launcherSettings = new Intent("com.archbrey.letters.Preferences.SettingsActivity");
                    mainContext.startActivity(launcherSettings);
                    break;
                case 2:
                    final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
                    mainContext.startActivity(Intent.createChooser(pickWallpaper, rMainSettings.getString(R.string.chooser_wallpaper)));
                    break;
                default:
                    break;
            } //switch (position)

            LaunchpadActivity.hideDrawerAllApps = false;
        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


    public GridView DrawSecondary (GridView getgridBox,Context c,Resources getR) {

        AppItem[] menuItems;

        //  SettingsActivity.infoView.setText(getR.getString(R.string.settings));
        mainMenuBox = getgridBox;
        mainContext = c;
        rMainSettings = getR;

        menuItems = new AppItem[10];
        for (int menuInc=0; menuInc<menuItems.length; menuInc++) menuItems[menuInc] = new AppItem();

        menuItems[0].label=getR.getString(R.string.apps);
        menuItems[1].label=getR.getString(R.string.bluetooth);
        menuItems[2].label=getR.getString(R.string.date_time);
        menuItems[3].label=getR.getString(R.string.display);
        menuItems[4].label=getR.getString(R.string.security);
        menuItems[5].label=getR.getString(R.string.sound);
        menuItems[6].label=getR.getString(R.string.storage);
        menuItems[7].label=getR.getString(R.string.wifi);
        menuItems[8].label=getR.getString(R.string.wireless);
        menuItems[9].label=getR.getString(R.string.system);


        LaunchpadActivity.drawDrawerBox.DrawBox(menuItems);
        setListenerSecondary();

        return mainMenuBox;

    } //public LinearLayout DrawSecondary ()

    public void setListenerSecondary () {
        mainMenuBox.setOnItemClickListener(new MenuSecondaryClickListener());
        mainMenuBox.setOnItemLongClickListener(new MenuLongClickNuller());
    } //public void setListenerSecondary ()


    private class MenuSecondaryClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Intent SystemIntent;

            switch (position) {
                //case 0: SystemIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS); break;
                case 0: SystemIntent = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS); break;
                case 1: SystemIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS); break;
                case 2: SystemIntent = new Intent(android.provider.Settings.ACTION_DATE_SETTINGS); break;
                case 3: SystemIntent = new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS); break;
                case 4: SystemIntent = new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS); break;
                case 5: SystemIntent = new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS); break;
                case 6: SystemIntent = new Intent(android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS); break;
                case 7: SystemIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS); break;
                case 8: SystemIntent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS); break;
                case 9: SystemIntent = new Intent(android.provider.Settings.ACTION_SETTINGS); break;
                default: SystemIntent = new Intent(android.provider.Settings.ACTION_SETTINGS); break;
            } //switch (position)

            try {
                mainContext.startActivity(SystemIntent);
            } catch (ActivityNotFoundException e) {
                mainContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }

            LaunchpadActivity.hideDrawerAllApps = false;
        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


    public class MenuLongClickNuller implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View viewItem, int pos, long l) {
            //do nothing
            return true;
        } //public boolean onItemLongClick(AdapterView<?> adapterView, View viewItem, int pos, long l)
    } // public class MenuLongClickListener implements OnItemLongClickListener


} // private class MenuClickListener implements AdapterView.OnItemClickListener
