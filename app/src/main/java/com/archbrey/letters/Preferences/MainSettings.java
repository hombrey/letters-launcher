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


    public MainSettings(){

    }


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        //com.archbrey.letters.Preferences.SettingsHolder holder;
        String[] menuItems;

        SettingsActivity.infoView.setText("Settings");
        SettingsActivity.menuArea = "MainSettings";
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        //holder = new com.archbrey.letters.Preferences.SettingsHolder();

        menuItems = new String[6];
        menuItems[0]=getR.getString(R.string.color_scheme);
        menuItems[1]=getR.getString(R.string.change_columns);
        menuItems[2]=getR.getString(R.string.drawer_textsize);
        menuItems[3] =getR.getString(R.string.custom_filter);
        menuItems[4] =getR.getString(R.string.custom_keypad);
        menuItems[5] =getR.getString(R.string.set_wallpaper);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



            switch (position) {
                case 0:
                    ColorSettings colorsettingsHandle;
                    colorsettingsHandle = new ColorSettings();
                    colorsettingsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 1:
                    ColumnSettings columnsettingsHandle;
                    columnsettingsHandle = new ColumnSettings();
                    columnsettingsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 2:
                    DrawerTextSize textSizeHandle;
                    textSizeHandle = new DrawerTextSize();
                    textSizeHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 3:
                    settingsContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    break;
                case 4:
                    HeightSettings heightHandle;
                    heightHandle = new HeightSettings();
                    heightHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 5:
                    final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
                    settingsContext.startActivity(Intent.createChooser(pickWallpaper, settingsContext.getString(R.string.chooser_wallpaper)));
                    break;
                default:
                    break;
            } //switch (position)


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


} //public class MainSettings
