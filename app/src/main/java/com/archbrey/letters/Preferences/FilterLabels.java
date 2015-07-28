package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
//import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.archbrey.letters.R;


public class FilterLabels {

    private static GridView mainMenuBox;
    private static Context settingsContext;
    private static Resources rMainSettings;
    private SettingsHolder settingsHelper;
    private static int clickedPosition;


    public GridView DrawBox (GridView getgridBox,Context c,Resources getR) {

        String[] menuItems;

        SettingsActivity.infoView.setText(getR.getString(R.string.custom_filter));
        SettingsActivity.menuArea = getR.getString(R.string.custom_filter);
        SettingsActivity.menuLevel=1;
        mainMenuBox = getgridBox;
        settingsContext = c;
        rMainSettings = getR;
        settingsHelper = new SettingsHolder();

        //take menu items directly from the settings

        new SettingsDrawer(settingsContext, mainMenuBox, SettingsActivity.filterCodes);
        setListener();

        return mainMenuBox;

    } //public LinearLayout DrawBox ()


    public void setListener() {mainMenuBox.setOnItemClickListener(new MenuClickListener());}
    private class MenuClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            SettingsActivity.menuArea = "EditFilter";
            SettingsActivity.menuLevel = 2;
            clickedPosition = position;
            SettingsActivity.SettingChanged = true;

            SettingsActivity.filterEditBox.setVisibility(View.VISIBLE);
            SettingsActivity.sdrawerBox.setVisibility(View.GONE);

           // public static EditText filterEditView;
          //  public static TextView filterInfoView;

            SettingsActivity.filterInfoView.setText(SettingsActivity.filterCodes[position]);
            SettingsActivity.filterInfoView.append(" -> ");
            SettingsActivity.filterEditView.setText(SettingsActivity.filterCodes[position]);

            SettingsActivity.filterEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        //do something
                       // SettingsActivity.filterInfoView.setText(SettingsActivity.filterEditView.getText());
                        SettingsActivity.filterEditBox.setVisibility(View.GONE);
                        SettingsActivity.sdrawerBox.setVisibility(View.VISIBLE);
                        SaveTypedValue(clickedPosition, SettingsActivity.filterEditView.getText().toString());
                        DrawBox(mainMenuBox, settingsContext, rMainSettings);
                       // String returnString = SettingsActivity.filterEditView.getText().toString();
                      // settingsHelper.setTypedString();

                    } //if (actionId == EditorInfo.IME_ACTION_DONE)
                    return false;
                } //public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            }); // public boolean onEditorAction(TextView v, int actionId, KeyEvent event)


        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)

        public void SaveTypedValue(int getPosition, String getString){

            SettingsActivity.filterCodes[getPosition] = getString;

            switch (getPosition) {
                case 0:
                       SettingsActivity.prefsEditor.putString ("filter0", getString);
                       break;
                case 1:
                    SettingsActivity.prefsEditor.putString ("filter1", getString);
                    break;
                case 2:
                    SettingsActivity.prefsEditor.putString ("filter2", getString);
                    break;
                case 3:
                    SettingsActivity.prefsEditor.putString ("filter3", getString);
                    break;
                case 4:
                    SettingsActivity.prefsEditor.putString ("filter4", getString);
                    break;
                case 5:
                    SettingsActivity.prefsEditor.putString ("filter5", getString);
                    break;
            } // switch (position)

            SettingsActivity.prefsEditor.commit();

        } //public void SaveTypedValue(int getPosition, String getString)

    } //private class MenuClickListener implements AdapterView.OnItemClickListener


}
