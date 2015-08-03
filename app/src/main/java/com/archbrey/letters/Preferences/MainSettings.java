package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;


public class MainSettings {


    public static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;


    public MainSettings(){

    }


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.settings));
        SettingsActivity.menuArea = "MainSettings";
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;

        menuItems = new String[7];
        menuItems[0]=getR.getString(R.string.color_scheme);
        menuItems[1]=getR.getString(R.string.custom_clock);
        menuItems[2]=getR.getString(R.string.change_columns);
        menuItems[3]=getR.getString(R.string.drawer_textsize);
        menuItems[4] =getR.getString(R.string.custom_filter);
        menuItems[5] =getR.getString(R.string.custom_keypad);
        menuItems[6] =getR.getString(R.string.handedness);


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
                    ClockSettings clocksettingsHandle;
                    clocksettingsHandle = new ClockSettings();
                    clocksettingsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 2:
                    ColumnSettings columnsettingsHandle;
                    columnsettingsHandle = new ColumnSettings();
                    columnsettingsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 3:
                    DrawerTextSize textSizeHandle;
                    textSizeHandle = new DrawerTextSize();
                    textSizeHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 4:
                    FilterLabels labelsHandle;
                    labelsHandle = new FilterLabels();
                    labelsHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                case 5:
                    HeightSettings heightHandle;
                    heightHandle = new HeightSettings();
                    heightHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);

                    break;
                case 6:
                    LandscapeHandedness handednessHandle;
                    handednessHandle = new LandscapeHandedness();
                    handednessHandle.DrawBox(mainMenuBox, settingsContext, rMainSettings);
                    break;
                default:
                    break;
            } //switch (position)


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


} //public class MainSettings
