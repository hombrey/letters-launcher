package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;


public class ColumnSettings {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;



    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;
        rMainSettings = getR;
        SettingsActivity.infoView.setText(getR.getString(R.string.change_columns));
        SettingsActivity.menuArea = getR.getString(R.string.change_columns);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;


        menuItems = new String[4];
        menuItems[0] ="one";
        menuItems[1] =getR.getString(R.string.two);
        menuItems[2] =getR.getString(R.string.three);
        menuItems[3] =getR.getString(R.string.four);

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
                    SettingsActivity.drawerColumns = 1;
                    break;
                case 1:
                    SettingsActivity.drawerColumns = 2;
                    break;
                case 2:
                    SettingsActivity.drawerColumns = 3;
                    break;
                case 3:
                    SettingsActivity.drawerColumns = 4;
                    break;
                default:
                    SettingsActivity.drawerColumns = 4;
                    break;
            } //switch (position)


            SettingsActivity.prefsEditor.putInt ("column_num",SettingsActivity.drawerColumns);
            SettingsActivity.prefsEditor.commit();
            DrawBox(mainMenuBox, settingsContext, rMainSettings);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener





} //public class ColumnSettings
