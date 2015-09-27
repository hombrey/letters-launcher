package com.archbrey.letters.Preferences;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;

public class ColorSettings {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.color_scheme));
        SettingsActivity.menuArea = getR.getString(R.string.color_scheme);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;

        menuItems = new String[14];
        menuItems[0] =getR.getString(R.string.dark_1);
        menuItems[1] =getR.getString(R.string.dark_2);
        menuItems[2] =getR.getString(R.string.dark_3);
        menuItems[3] =getR.getString(R.string.dark_4);
        menuItems[4] =getR.getString(R.string.dark_5);
        menuItems[5] =getR.getString(R.string.dark_6);
        menuItems[6] =getR.getString(R.string.dark_7);
        menuItems[7] =getR.getString(R.string.light_1);
        menuItems[8] =getR.getString(R.string.light_2);
        menuItems[9] =getR.getString(R.string.light_3);
        menuItems[10] =getR.getString(R.string.light_4);
        menuItems[11] =getR.getString(R.string.light_5);
        menuItems[12] =getR.getString(R.string.light_6);
        menuItems[13] =getR.getString(R.string.light_7);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()

    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}


    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_0);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_2);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark1");
                    break;
                case 1:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_2);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_4);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark2");
                    break;
                case 2:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_4);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_6);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark3");
                    break;
                case 3:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_6);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_8);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark4");
                    break;
                case 4:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_8);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_A);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark5");
                    break;
                case 5:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_A);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_D);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark6");
                    break;
                case 6:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.white);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.Black_D);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.Black_F);
                    SettingsActivity.prefsEditor.putString ("colorscheme","dark7");
                    break;
                case 7:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_0);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_2);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light1");
                    break;
                case 8:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_2);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_4);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light2");
                    break;
                case 9:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_4);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_6);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light3");
                    break;
                case 10:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_6);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_8);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light4");
                    break;
                case 11:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_8);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_A);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light5");
                    break;
                case 12:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_A);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_D);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light6");
                    break;
                case 13:
                    SettingsActivity.textColor = rMainSettings.getColor(R.color.black);
                    SettingsActivity.backColor = rMainSettings.getColor(R.color.White_D);
                    SettingsActivity.backerColor = rMainSettings.getColor(R.color.White_F);
                    SettingsActivity.prefsEditor.putString ("colorscheme","light7");
                    break;
                default:
                    break;
            } //switch (position)

            SettingsActivity.SettingChanged = true;
            SettingsActivity.prefsEditor.commit();
            DrawBox(mainMenuBox, settingsContext, rMainSettings);
            SettingsActivity.infoBox.setBackgroundColor(SettingsActivity.backerColor);
            SettingsActivity.infoView.setTextColor(SettingsActivity.textColor);


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener




} //public class ColorSettings
