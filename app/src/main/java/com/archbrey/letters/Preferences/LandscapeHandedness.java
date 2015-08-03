package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.archbrey.letters.R;

public class LandscapeHandedness {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    public static LinearLayout viewpadBox;
    private static int sample_pad_height;
    int screenWidth;
    Display display;
    Point size;



    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {


        display = SettingsActivity.setActivity.getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        screenWidth = size.x;


        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.custom_keypad));
        SettingsActivity.menuArea = getR.getString(R.string.custom_keypad);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;

        menuItems = new String[2];
        menuItems[0] =getR.getString(R.string.lefthanded);
        menuItems[1] =getR.getString(R.string.righthanded);

        new SettingsDrawer(settingsContext, mainMenuBox, menuItems);
        setListener();

        sample_pad_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.keyboardHeight*4,
                rMainSettings.getDisplayMetrics());

        SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.backSelectColor);
        RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                screenWidth/2,
                screenWidth/2);

        viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());
        if (SettingsActivity.handedness.equals("right")) {
            viewboxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else
            viewboxParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);
        SettingsActivity.viewSample.setText(R.string.keyboardhere);

        return mainMenuBox;

    } //public LinearLayout DrawBox ()


    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //noinspection SimplifiableIfStatement
            switch (position) {
                case 0:
                    SettingsActivity.handedness = "left";
                    break;
                case 1:
                    SettingsActivity.handedness = "right";
                    break;
            } //switch (position)

            SettingsActivity.SettingChanged = true;
            SettingsActivity.prefsEditor.putString ("handedness", SettingsActivity.handedness);
            SettingsActivity.prefsEditor.commit();

            SettingsActivity.viewpadBox.setBackgroundColor(SettingsActivity.backSelectColor);
            RelativeLayout.LayoutParams viewboxParams = new RelativeLayout.LayoutParams(
                    screenWidth/2,
                    screenWidth/2);
            viewboxParams.addRule(RelativeLayout.ABOVE, SettingsActivity.sdrawerBox.getId());

            if (SettingsActivity.handedness.equals("right")) {
                viewboxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else
                viewboxParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

            SettingsActivity.settingsScreen.addView(SettingsActivity.viewpadBox, viewboxParams);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class MenuClickListener implements AdapterView.OnItemClickListener




} //public class LandscapeHandedness
