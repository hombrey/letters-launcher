package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;


public class MainSettings {

   // private static SettingsDrawerAdapter menuDrawerAdapter;
   // private static String[] showItem;
    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    private static com.archbrey.letters.Preferences.SettingsHolder holder;
    private static String[] menuItems;

    public MainSettings(){

    }


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        SettingsActivity.menuArea = "MainSettings";
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();

        menuItems = new String[4];
        menuItems[0] =getR.getString(R.string.color_scheme);
        menuItems[1] =getR.getString(R.string.custom_filter);
        menuItems[2] =getR.getString(R.string.custom_keypad);
        menuItems[3] =getR.getString(R.string.set_wallpaper);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            ColorSettings colorsettingsHandle;

            switch (position) {
                case 0:
                    colorsettingsHandle = new ColorSettings();
                    colorsettingsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 1:
                    settingsContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    break;
                case 3:
                    final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
                    settingsContext.startActivity(Intent.createChooser(pickWallpaper, settingsContext.getString(R.string.chooser_wallpaper)));
                    break;
                default:
                    break;
            } //switch (position)


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


} //public class MainSettings
