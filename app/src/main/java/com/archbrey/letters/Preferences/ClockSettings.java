package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
//import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.OptionsCall;
import com.archbrey.letters.R;

public class ClockSettings {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    public static LinearLayout viewpadBox;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.custom_clock));
        SettingsActivity.menuArea = getR.getString(R.string.custom_clock);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;

        menuItems = new String[5];
        menuItems[0] =getR.getString(R.string.clock_invisible);
        menuItems[1] =getR.getString(R.string.clock_transparent);
        menuItems[2] =getR.getString(R.string.clock_minimum);
        menuItems[3] =getR.getString(R.string.clock_middle);
        menuItems[4] =getR.getString(R.string.clock_maximum);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.clockBack);
        RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

        SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);
        SettingsActivity.viewSample.setText(R.string.sample_clock);

        return mainMenuBox;

    } //public GridView DrawBox ()


    public void setListener() {
        mainMenuBox.setOnItemClickListener(new MenuClickListener());
    } // public void setListener()

    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    SettingsActivity.clockVisibility = 0;
                    SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);
                    break;
                case 1:
                    SettingsActivity.clockVisibility = 1;
                    break;
                case 2:
                    SettingsActivity.clockVisibility = 2;
                    break;
                case 3:
                    SettingsActivity.clockVisibility = 3;
                    break;
                case 4:
                    SettingsActivity.clockVisibility = 4;
                    break;
                default:
                    SettingsActivity.clockVisibility = 5;
                    break;
            } //switch (position)

            SettingsActivity.SettingChanged = true;
            SettingsActivity.prefsEditor.putInt("clockVisibility", SettingsActivity.clockVisibility);
            SettingsActivity.prefsEditor.commit();
            setBackground();

            SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.clockBack);
            RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

            SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

            if (SettingsActivity.clockVisibility != 0) {
                SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);
            } //if (SettingsActivity.clockVisibility != 0)

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener


    public void setBackground() {

        SettingsActivity.clockBack = rMainSettings.getColor(R.color.transparent);

        if (LaunchpadActivity.colorScheme.equals("black")) {
            switch (SettingsActivity.clockVisibility) {
                case 2: SettingsActivity.clockBack = rMainSettings.getColor(R.color.Blackless_transparent);break;
                case 3: SettingsActivity.clockBack = rMainSettings.getColor(R.color.Black_transparent);break;
                case 4: SettingsActivity.clockBack = rMainSettings.getColor(R.color.Blacker_transparent);break;
                default: break;
            } //switch (SettingsActivity.clockVisibility)
        } // if (LaunchpadActivity.colorScheme.equals("black"))
        else {
            switch (SettingsActivity.clockVisibility) {
                case 2: SettingsActivity.clockBack = rMainSettings.getColor(R.color.Whiteless_transparent);break;
                case 3: SettingsActivity.clockBack = rMainSettings.getColor(R.color.White_transparent);break;
                case 4: SettingsActivity.clockBack = rMainSettings.getColor(R.color.Whiter_transparent);break;
                default: break;
            } //switch (SettingsActivity.clockVisibility)

        } //else of if (LaunchpadActivity.colorScheme.equals("black"))

    } //public void setBackground()


} //public class ClockSettings
