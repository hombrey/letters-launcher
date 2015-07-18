package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.archbrey.letters.R;


public class DrawerTextSize {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    private static com.archbrey.letters.Preferences.SettingsHolder holder;
    private static String[] menuItems;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        SettingsActivity.infoView.setText(getR.getString(R.string.drawer_textsize));
        SettingsActivity.menuArea = getR.getString(R.string.drawer_textsize);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();

        menuItems = new String[6];
        menuItems[0] =getR.getString(R.string.fifteen);
        menuItems[1] =getR.getString(R.string.seventeen);
        menuItems[2] =getR.getString(R.string.nineteen);
        menuItems[3] =getR.getString(R.string.twentyone);
        menuItems[4] =getR.getString(R.string.twentythree);
        menuItems[5] =getR.getString(R.string.twentyfive);

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
                    SettingsActivity.drawerTextSize = 15;
                    break;
                case 1:
                    SettingsActivity.drawerTextSize = 17;
                    break;
                case 2:
                    SettingsActivity.drawerTextSize = 19;
                    break;
                case 3:
                    SettingsActivity.drawerTextSize = 21;
                    break;
                case 4:
                    SettingsActivity.drawerTextSize = 23;
                    break;
                case 5:
                    SettingsActivity.drawerTextSize = 25;
                    break;
                default:
                    SettingsActivity.drawerTextSize = 17;
                    break;
            } //switch (position)


            SettingsActivity.prefsEditor.putInt ("drawerTextSize",SettingsActivity.drawerTextSize);
            SettingsActivity.prefsEditor.commit();
            DrawBox(mainMenuBox, settingsContext, rMainSettings);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener



}
