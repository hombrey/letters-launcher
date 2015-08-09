package com.archbrey.letters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.Preferences.SettingsActivity;


public class OptionsCall {

    public static GridView mainMenuBox;
    private static Context mainContext;
    private static Resources rMainSettings;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

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
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()


    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}

    private class MenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            switch (position) {
                case 0:
                    mainContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
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


} // private class MenuClickListener implements AdapterView.OnItemClickListener
