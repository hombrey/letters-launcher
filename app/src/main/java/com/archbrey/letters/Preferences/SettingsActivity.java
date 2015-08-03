package com.archbrey.letters.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.archbrey.letters.DrawKeypadBox;
import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.R;

//import java.lang.reflect.Method;

public class SettingsActivity extends Activity {

    public Context C;
    private Resources r;
    public static RelativeLayout settingsScreen;

    public static LinearLayout infoBox;
    public static TextView infoView;


    public static LinearLayout filterEditBox;
    public static EditText filterEditView;
    public static TextView filterInfoView;
    public static Button filterEditDone;
    public static TextView filterSpacer;

    public static DrawKeypadBox viewpadBoxHandle;

    public static  LinearLayout viewpadBox;
    public static  TextView viewSample;

    public static View sdrawerBox ;

    private static GridView gridDrawer;
    private static MainSettings mainsettingsHandle;
    //private LinearLayout setColorsBox;

    public static int menuLevel;
    public static String menuArea;

    public static SharedPreferences.Editor prefsEditor;
    public static SharedPreferences prefs;
    public static String prefName = "LettersPrefs";

    public static int textColor;
    public static int backColor;
    public static int backerColor;
    public static int backSelectColor;
    public static int transparent;
    public static int clockBack;


    public static int drawerColumns;
    public static int drawerTextSize;

    public static int keyboardHeight;
    public static int filterHeight;

    public static int clockVisibility;

    public static String[] filterCodes;

    public static boolean SettingChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //keep layout in portrait

        com.archbrey.letters.Preferences.SettingsHolder holder;
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        prefsEditor = prefs.edit();

       SettingChanged = false;

        r = getResources();
        C = this;
        menuLevel = 0;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();
        holder.setSettingsContext(this);
        holder.setResources(r);


        settingsScreen = new RelativeLayout(this);
        //settingsScreen.setOrientation(LinearLayout.VERTICAL);
        settingsScreen.setGravity(Gravity.BOTTOM);

        //draw menu drawer
        sdrawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

        assembleScreen();

        setContentView(settingsScreen);

        gridDrawer = (GridView) findViewById(R.id.drawer_content);


        mainsettingsHandle = new MainSettings();
        mainsettingsHandle.DrawBox(gridDrawer, this, r);


    } // protected void onCreate(Bundle savedInstanceState)


    @Override
    protected void onResume() {
        super.onResume();

        if (menuLevel == 0){
            mainsettingsHandle.DrawBox(gridDrawer, this, r);
        } //if (menuLevel == 0)

    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();


    } //protected void onStart()

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if( (keyCode == KeyEvent.KEYCODE_BACK) && (menuLevel>0) ) {

            filterEditBox.setVisibility(View.GONE);
            sdrawerBox.setVisibility(View.VISIBLE);

            if (menuLevel == 0) {
                try{finish();}catch (Exception e) {}
                // startActivity(LaunchpadActivity.reStarterIntent);
            } //if if (menuLevel == 0)

            if (menuLevel ==1) {
                //mainsettingsHandle = new MainSettings();
                mainsettingsHandle.DrawBox(gridDrawer, this, r);
                settingsScreen.removeView(viewpadBox);
                menuLevel --;
                return true;
                 } //if (menuLevel ==1)

            if (menuArea == "EditFilter") {
                FilterLabels labelsHandle;
                labelsHandle = new FilterLabels();
                labelsHandle.DrawBox(MainSettings.mainMenuBox, this, r);
                return true;
            }

          // onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStop(){
        super.onStop();

        settingsScreen.removeView(viewpadBox);
        if (menuLevel > 0) menuLevel = 0;
       // try{finish();}catch (Exception e) {}
        //kill activity when not visible to force onCreate() when settings is called again
       //  if (menuLevel > 0) finish();

    } //protected void onStart()


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_MAIN.equals(intent.getAction()) ) {
            // Log.i("MyLauncher", "onNewIntent: HOME Key");
            SettingsActivity.menuLevel=0;
            // SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

        } //if (Intent.ACTION_MAIN.equals(intent.getAction()))


    } //protected void onNewIntent(Intent intent)




    public void finish() {

        //Intent data = new Intent();
        //data.putExtra("returnData", returnString);
        //setResult(RESULT_OK, data);

        //restart launcher after finishing with settings;
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        super.finish();
    }


    private void assembleScreen(){

        filterEditBox = new LinearLayout(this);
        filterEditBox.setOrientation(LinearLayout.HORIZONTAL);
        filterEditBox.setBackgroundColor(SettingsActivity.backColor);

        filterEditView = new EditText (this);
        filterEditView.setBackgroundColor(SettingsActivity.backSelectColor);
        filterEditView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        filterEditView.setSingleLine(true);
        //filterEditView.setText("THIS");

        filterInfoView = new TextView(this);
      //  filterInfoView.setBackgroundColor(SettingsActivity.backColor);
        filterInfoView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);



        filterSpacer = new TextView(this);
      //  filterSpacer.setBackgroundColor(SettingsActivity.backColor);
        filterSpacer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        filterSpacer.setText("            ");

        filterEditDone = new Button(this);
        filterEditDone.setBackgroundColor(SettingsActivity.backColor);
        filterEditDone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        filterEditDone.setText("DONE");

        filterEditBox.addView(filterSpacer);
        filterEditBox.addView(filterInfoView);
        filterEditBox.addView(filterEditView);


        filterEditBox.setId(R.id.editFilterBox);


        infoBox = new LinearLayout(this);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        infoBox.setBackgroundColor(SettingsActivity.backerColor);

        infoView = new TextView(this);
        infoView.setText("Settings");
        infoView.setGravity(Gravity.CENTER_HORIZONTAL);
        infoView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        infoView.setTextColor(SettingsActivity.textColor);

        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, //width
                LinearLayout.LayoutParams.WRAP_CONTENT); //height
        infoViewParams.setMargins(0, 20, 0, 20);
        infoBox.addView(infoView, infoViewParams);

        sdrawerBox.setId(R.id.settingsdrawerBox);
        infoBox.setId(R.id.infoBox);

        RelativeLayout.LayoutParams filterEditParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filterEditParams.addRule(RelativeLayout.ABOVE, infoBox.getId());


        RelativeLayout.LayoutParams sdrawerParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        sdrawerParams.addRule(RelativeLayout.ABOVE, infoBox.getId());

        RelativeLayout.LayoutParams infoBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        infoBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        viewpadBox = new LinearLayout(this);
        viewSample = new TextView(this);
        viewSample.setTypeface(null, Typeface.ITALIC);
        viewSample.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        viewSample.setTextColor(SettingsActivity.textColor);
        viewSample.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams viewSampleParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        SettingsActivity.viewpadBox.addView(SettingsActivity.viewSample, viewSampleParams);

        settingsScreen.addView(filterEditBox, filterEditParams);
        settingsScreen.addView(sdrawerBox, sdrawerParams);
        settingsScreen.addView(infoBox,infoBoxParams);
        filterEditBox.setVisibility(View.GONE);


    }


}
